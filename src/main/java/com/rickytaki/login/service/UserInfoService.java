package com.rickytaki.login.service;

import com.rickytaki.login.dao.AddressDao;
import com.rickytaki.login.dao.UserInfoDao;
import com.rickytaki.login.model.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor
public class UserInfoService {

    @NotNull
    private final UserInfoDao userInfoDao;

    @NotNull
    private final AddressDao addressDao;

    @Transactional
    public void save (@NotNull UserInfo userInfo) {
        userInfoDao.save(userInfo);
        addressDao.save(userInfo);
    }

    @Cacheable(value = "userCache", key = "#name")
    public UserInfo findByName (String name) {
        return userInfoDao.findByName(name).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
