package com.parceldelivery.msauthuser.model;

import lombok.Data;

@Data
public class UserRequestDto {

    private String fullName;
    private String username;
    private String password;

}
