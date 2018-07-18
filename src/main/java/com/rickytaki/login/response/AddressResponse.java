package com.rickytaki.login.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressResponse implements Serializable {

    private String street;
    private int number;
    private String zipCode;
}
