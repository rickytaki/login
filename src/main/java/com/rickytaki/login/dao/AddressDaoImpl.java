package com.rickytaki.login.dao;

import com.rickytaki.login.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl implements AddressDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Address address) {
        String query = "INSERT INTO address(email, street, number, zip_code) VALUES (?,?,?,?)";
        jdbcTemplate.update(query, address.getEmail(), address.getStreet(), address.getNumber(), address.getZipCode());
    }
}
