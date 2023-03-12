package com.parceldelivery.gateway.controller;

import com.parceldelivery.gateway.client.user.LoginClientRequestDto;
import com.parceldelivery.gateway.client.user.MsAuthUserClient;
import com.parceldelivery.gateway.client.user.RegisterUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final MsAuthUserClient msAuthUserClient;

    @PostMapping("/auth/login")
    public Object login(@RequestBody LoginClientRequestDto requestDto) {
        return msAuthUserClient.login(requestDto);
    }

    @PostMapping("/auth/register")
    public void register(@RequestBody RegisterUserRequestDto requestDto) {
        msAuthUserClient.register(requestDto);
    }

    @PostMapping("/auth/verify")
    public Object verify(HttpServletRequest httpServletRequest) {
        return msAuthUserClient.verify(httpServletRequest.getHeader("Authorization"));
    }

}
