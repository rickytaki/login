package com.rickytaki.login.controller;

import com.rickytaki.login.model.UserInfo;
import com.rickytaki.login.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService service;

    @PostMapping (path = "/create", consumes = "application/json")
    public void createUser (@RequestBody UserInfo userInfo) {
        service.save(userInfo);
    }

    @GetMapping ("/findByName/{name}")
    public ResponseEntity<UserInfo> findByName (@PathVariable String name) {
        UserInfo user = service.findByName(name);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
