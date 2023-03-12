package com.parceldelivery.gateway.client.admin;

import com.parceldelivery.gateway.client.user.ProfileClientResponseDto;
import com.parceldelivery.gateway.client.user.RegisterUserRequestDto;
import com.parceldelivery.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${client.auth-admin.name}", url = "${client.auth-admin.url}",
        configuration = FeignClientConfiguration.class)
public interface MsAuthAdminClient {

    @PostMapping("/v1/auth/login")
    Object login(@RequestBody LoginClientRequestDto loginClientRequestDto);

    @GetMapping("/v1/auth/profile")
    ProfileClientResponseDto verify(@RequestHeader(name = "Authorization") String token);

    @PostMapping("/v1/auth/register")
    void register(@RequestBody RegisterUserRequestDto registerUserRequestDto);

}
