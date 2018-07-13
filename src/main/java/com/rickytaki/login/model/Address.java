package com.rickytaki.login.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {

    private String email;
    private String street;
    private int number;
    private int zipCode;
}
