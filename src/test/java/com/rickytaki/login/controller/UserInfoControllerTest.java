package com.rickytaki.login.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rickytaki.login.controller.UserInfoController;
import com.rickytaki.login.model.Address;
import com.rickytaki.login.model.UserInfo;
import com.rickytaki.login.service.UserInfoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserInfoController.class)
public class UserInfoControllerTest {

    @MockBean
    private UserInfoService service;

    @Autowired
    MockMvc mvc;

    private ObjectMapper jsonMapper = new ObjectMapper();

    private UserInfoController controller;

    private UserInfo user;

    @Before
    public void init(){
        this.controller = new UserInfoController(service);

        user = new UserInfo();
        user.setEmail("controller@controller.com");
        user.setPassword("controller123");
        user.setAge(33);
        user.setName("controller");

        Address address = new Address();
        address.setZipCode(333333);
        address.setStreet("Controller St");
        address.setNumber(33);
        address.setEmail(user.getEmail());
        user.setAddress(address);
    }

    @Test
    public void whenNotNull_ShouldCreateUser() throws Exception{
        doNothing().when(service).save(any());
        mvc.perform(post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
        verify(service, times(1)).save(user);
    }

    @Test
    public void givenUser_ShouldRetrieve() throws Exception{
        when(service.findByName(user.getName())).thenReturn(user);
        mvc.perform(get("/findByName/controller"))
//        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("controller")));
        Assert.assertEquals(user.getName(), controller.findByName(user.getName()).getBody().getName());
    }
}
