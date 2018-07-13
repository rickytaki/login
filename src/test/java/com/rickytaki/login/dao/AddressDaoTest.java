package com.rickytaki.login.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@PropertySource("application-test.properties")
@JdbcTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ContextConfiguration(classes = {AddressDaoImpl.class})
public class AddressDaoTest {

    @Autowired
    AddressDao dao;

    @Test
    public void WhenNotNull_ShouldSaveAndRetrieve () {
        Adress
    }
}
