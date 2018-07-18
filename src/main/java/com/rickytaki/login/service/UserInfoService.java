package com.rickytaki.login.service;

import com.rickytaki.login.dao.AddressDao;
import com.rickytaki.login.dao.UserInfoDao;
import com.rickytaki.login.model.UserInfo;
import com.rickytaki.login.response.UserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {

    @NonNull
    private final UserInfoDao userInfoDao;

    @NonNull
    private final AddressDao addressDao;

    @NonNull
    private BCryptPasswordEncoder encoder;

    @NonNull
    private MapperFacade mapper;

    @Transactional
    public void save (@NotNull UserInfo userInfo) {
        String encoded = encoder.encode(userInfo.getPassword());
        userInfo.setPassword(encoded);
        userInfoDao.save(userInfo);
        addressDao.save(userInfo);
    }

    @Cacheable(value = "byName", key = "#name")
    @PreAuthorize("#name == authentication.principal.name")
    public UserInfoResponse findByName (String name) throws RuntimeException{

        return  userInfoDao.findByName(name)
                .map(user -> mapper.map(user, UserInfoResponse.class))
                .orElseThrow(() -> new RuntimeException("User " + name + " Not Found"));
    }

    @Cacheable(value = "byEmail", key = "#email")
    @PreAuthorize("#email == authentication.principal.username")
    public UserInfoResponse findByEmail (String email) throws  RuntimeException {

        return userInfoDao.findByEmail(email)
                .map(user -> mapper.map(user, UserInfoResponse.class))
                .orElseThrow(() -> new RuntimeException("User " + email + " Not Found"));
    }

    @Override
    @Cacheable(value = "login", key = "#s")
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userInfoDao.findByEmail(s).
                orElseThrow(() -> new UsernameNotFoundException("User " + s + " not found"));
    }
}
