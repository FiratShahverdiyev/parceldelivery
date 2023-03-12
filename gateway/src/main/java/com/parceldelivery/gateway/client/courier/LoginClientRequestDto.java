package com.parceldelivery.gateway.client.courier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginClientRequestDto {

    private String username;
    private String password;

}
