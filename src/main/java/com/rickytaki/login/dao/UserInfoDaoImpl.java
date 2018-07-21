package com.rickytaki.login.dao;

import com.rickytaki.login.model.Address;
import com.rickytaki.login.model.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserInfoDaoImpl implements UserInfoDao {


    private final JdbcTemplate jdbcTemplate ;

    @Override
    public void save(UserInfo userInfo) {
        String query = " INSERT INTO user_info (name, email, password, age) VALUES (?,?,?,?)";
        jdbcTemplate.update(query, userInfo.getName(), userInfo.getEmail(), userInfo.getPassword(), userInfo.getAge());
    }

    @Override
    public Optional<UserInfo> findByName(String name) {
        String query = "SELECT u.name, u.age, u.email, u.password, ad.street, ad.number, ad.zip_code " +
                "FROM user_info AS u LEFT JOIN address AS ad ON (u.email = ad.email) WHERE u.name=? ";
        return getMapRow(name, query);
    }

    @Override
    public Optional<UserInfo> findByEmail(String email) {

        String query = "SELECT u.name, u.age, u.email, u.password, ad.street, ad.number, ad.zip_code " +
                "FROM user_info AS u LEFT JOIN address AS ad ON (u.email = ad.email) WHERE u.email=? ";
        return getMapRow(email, query);
    }

    private Optional<UserInfo> getMapRow(String param, String query) {
        try {
            Optional<UserInfo> found = jdbcTemplate.queryForObject(query, (ResultSet rs, int rowNumber) -> {
                UserInfo user = new UserInfo();
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                Address address = new Address();
                address.setStreet(rs.getString("street"));
                address.setNumber(rs.getInt("number"));
                address.setZipCode(rs.getString("zip_code"));
                user.setAddress(address);
                return Optional.ofNullable(user);
            }, param);
            return found;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
