package com.parceldelivery.gateway.client.user;

import com.parceldelivery.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${client.auth-user.name}", url = "${client.auth-user.url}",
        configuration = FeignClientConfiguration.class)
public interface MsAuthUserClient {

    @PostMapping("/v1/auth/login")
    Object login(@RequestBody LoginClientRequestDto loginClientRequestDto);

    @PostMapping("/v1/auth/register")
    void register(@RequestBody RegisterUserRequestDto registerUserRequestDto);

    @GetMapping("/v1/auth/profile")
    ProfileClientResponseDto verify(@RequestHeader(name = "Authorization") String token);

}
