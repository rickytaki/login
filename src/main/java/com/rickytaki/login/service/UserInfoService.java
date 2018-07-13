package com.rickytaki.login.service;

import com.rickytaki.login.dao.AddressDao;
import com.rickytaki.login.dao.UserInfoDao;
import com.rickytaki.login.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AddressDao addressDao;

    @Transactional
    public void save (@NotNull UserInfo userInfo) {
        userInfoDao.save(userInfo);
        addressDao.save(userInfo.getAddress());
    }

    @Cacheable(value = "userCache", key = "#name")
    public UserInfo findByName (String name) {
        return userInfoDao.findByName(name).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
