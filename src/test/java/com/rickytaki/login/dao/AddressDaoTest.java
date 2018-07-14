package com.rickytaki.login.dao;

import com.rickytaki.login.model.Address;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;

@RunWith(SpringRunner.class)
@PropertySource("application-test.properties")
@JdbcTest
@Sql(scripts = {"classpath:schema.sql", "classpath:addressData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ContextConfiguration(classes = {AddressDaoImpl.class})
public class AddressDaoTest {

    @Autowired
    AddressDao dao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void WhenNotNull_ShouldSaveAndRetrieve () {

        Address address = new Address();
        address.setZipCode(123123);
        address.setStreet("TestAdress street");
        address.setNumber(123);
        address.setEmail("addressTest@addressTest.com");

        dao.save(address);

        Address found = jdbcTemplate.queryForObject("SELECT * FROM address WHERE email = ?", (ResultSet rs, int rowNumber) -> {
            Address ad = new Address();
            ad.setEmail(rs.getString("email"));
            ad.setNumber(rs.getInt("number"));
            ad.setStreet(rs.getString("street"));
            ad.setZipCode(rs.getInt("zip_code"));

            return ad;
        }, address.getEmail());
        Assert.assertEquals(address.getStreet(), found.getStreet());
    }
}
