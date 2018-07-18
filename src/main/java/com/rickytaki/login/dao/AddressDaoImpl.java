package com.rickytaki.login.dao;

import com.rickytaki.login.model.Address;
import com.rickytaki.login.model.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
@AllArgsConstructor
public class AddressDaoImpl implements AddressDao {

    @NotNull
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Address address) {

        String query = "INSERT INTO address(email, street, number, zip_code) VALUES (?,?,?,?)";
        jdbcTemplate.update(query, address.getEmail(), address.getStreet(), address.getNumber(), address.getZipCode());
    }


}
