package com.rickytaki.login.dao;

import com.rickytaki.login.model.Address;
import com.rickytaki.login.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    @Autowired
    JdbcTemplate jdbcTemplate ;

    @Override
    public void save(UserInfo userInfo) {
        String query = " INSERT INTO user_info (name, email, password, age) VALUES (?,?,?,?)";
        jdbcTemplate.update(query, userInfo.getName(), userInfo.getEmail(), userInfo.getPassword(), userInfo.getAge());
    }

    @Override
    public Optional<UserInfo> findByName(String name) {
        String query = "SELECT u.name, u.age, u.email, ad.street, ad.number, ad.zip_code FROM user_info AS u LEFT JOIN address AS ad ON (u.email = ad.email) WHERE u.name=? ";

        Optional<UserInfo> found = jdbcTemplate.queryForObject(query, (ResultSet rs, int rowNumber) -> {
           UserInfo user = new UserInfo();
           user.setName(rs.getString("name"));
           user.setAge(rs.getInt("age"));
           user.setEmail(rs.getString("email"));

           Address address = new Address();
           address.setStreet(rs.getString("street"));
           address.setNumber(rs.getInt("number"));
           address.setZipCode(rs.getInt("zip_code"));
           user.setAddress(address);
           return Optional.ofNullable(user);
        }, name);
        return found;
    }
}
