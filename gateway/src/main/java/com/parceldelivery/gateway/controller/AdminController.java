package com.parceldelivery.gateway.controller;

import com.parceldelivery.gateway.client.admin.LoginClientRequestDto;
import com.parceldelivery.gateway.client.admin.MsAuthAdminClient;
import com.parceldelivery.gateway.client.user.RegisterUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
public class AdminController {

    private final MsAuthAdminClient msAuthAdminClient;

    @PostMapping("/auth/login")
    public Object login(@RequestBody LoginClientRequestDto requestDto) {
        return msAuthAdminClient.login(requestDto);
    }

    @PostMapping("/auth/register")
    public void register(@RequestBody RegisterUserRequestDto requestDto) {
        msAuthAdminClient.register(requestDto);
    }

    @PostMapping("/auth/verify")
    public Object verify(HttpServletRequest httpServletRequest) {
        return msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
    }

}
