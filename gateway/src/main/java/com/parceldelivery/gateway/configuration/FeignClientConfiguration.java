package com.parceldelivery.gateway.configuration;

import com.parceldelivery.gateway.error.GlobalErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.parceldelivery.gateway.client")
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GlobalErrorDecoder();
    }

}
