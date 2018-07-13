package com.rickytaki.login.service;

import com.rickytaki.login.dao.AddressDao;
import com.rickytaki.login.dao.UserInfoDao;
import com.rickytaki.login.model.Address;
import com.rickytaki.login.model.UserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserInfoServiceTest {

    @Mock
    private UserInfoDao dao;

    @Mock
    private AddressDao addressDao;

    @InjectMocks
    private UserInfoService service;

    UserInfo user = new UserInfo();

    @Before
    public void init() {
        user.setName("testing");
        user.setAge(22);
        user.setEmail("testing@testing.com");
        Address address = new Address();
        address.setEmail(user.getEmail());
        address.setZipCode(010101010);
        address.setNumber(22);
        address.setStreet("Testing street");
        user.setAddress(address);
    }

    public void finalize() {
        user = null;
    }

    @Test
    public void shouldCreateNewUser() {
        doNothing().when(dao).save(user);
        doNothing().when(addressDao).save(user.getAddress());
        service.save(user);
        verify(dao, times(1)).save(user);
        verify(addressDao, times(1)).save(user.getAddress());
    }

    @Test
    public void whenNotNull_ShouldReturnUserInfo() {
        when(dao.findByName("testing")).thenReturn(Optional.of(user));
        UserInfo found = service.findByName("testing");

        Assert.assertEquals(user.getAddress().getStreet(), found.getAddress().getStreet());
        verify(dao).findByName("testing");
    }

    @Test(expected = RuntimeException.class)
    public void whenNotFound_ShouldReturnException() {
        service.findByName("Arlindo");
    }
}
