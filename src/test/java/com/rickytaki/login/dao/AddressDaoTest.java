package com.rickytaki.login.dao;

import com.rickytaki.login.model.Address;
import com.rickytaki.login.model.UserInfo;
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
@ContextConfiguration(classes = {AddressDaoImpl.class})
public class AddressDaoTest {

    @Autowired
    AddressDao dao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Sql(scripts = {"classpath:schema.sql", "classpath:addressData.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void WhenNotNull_ShouldSaveAndRetrieve () {

        UserInfo user = new UserInfo();
        user.setEmail("addressTest@addressTest.com");
        Address address = new Address();
        address.setZipCode("123123");
        address.setStreet("TestAdress street");
        address.setNumber(123);
        user.setAddress(address);

        dao.save(user);

        Address found = jdbcTemplate.queryForObject("SELECT * FROM address WHERE email = ?", (ResultSet rs, int rowNumber) -> {
            Address ad = new Address();
            ad.setEmail(rs.getString("email"));
            ad.setNumber(rs.getInt("number"));
            ad.setStreet(rs.getString("street"));
            ad.setZipCode(rs.getString("zip_code"));

            return ad;
        }, user.getEmail());
        Assert.assertEquals(address.getStreet(), found.getStreet());
    }
}
