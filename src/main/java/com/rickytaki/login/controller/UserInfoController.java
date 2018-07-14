package com.rickytaki.login.controller;

import com.rickytaki.login.model.UserInfo;
import com.rickytaki.login.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
public class UserInfoController {

    @NotNull
    private final UserInfoService service;

    @PostMapping (path = "/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser (@RequestBody UserInfo userInfo) {
        service.save(userInfo);
    }

    @GetMapping (value = "/findByName/{name}", produces = "application/json")
    public ResponseEntity<UserInfo> findByName (@PathVariable String name) {
        UserInfo user = service.findByName(name);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
