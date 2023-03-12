package com.parceldelivery.gateway.client.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequestDto {

    private String fullName;
    private String username;
    private String password;

}
