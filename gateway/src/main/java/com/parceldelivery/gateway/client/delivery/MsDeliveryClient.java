package com.parceldelivery.gateway.client.delivery;

import com.parceldelivery.gateway.client.user.ProfileClientResponseDto;
import com.parceldelivery.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${client.delivery.name}", url = "${client.delivery.url}",
        configuration = FeignClientConfiguration.class)
public interface MsDeliveryClient {

    @PostMapping("/v1/deliveries/")
    Object create(@RequestBody DeliveryClientRequestDto deliveryClientRequestDto);

    @GetMapping("/v1/deliveries/")
    ProfileClientResponseDto verify(@RequestHeader(name = "Authorization") String token);

    @GetMapping("/v1/deliveries/couriers/{id}/deliveries")
    Object getDeliveriesByCourierId(@PathVariable Integer id, @RequestParam int page, @RequestParam int size);

    @PutMapping("/v1/deliveries/{id}/change-status")
    void changeStatus(@PathVariable Integer id, @RequestParam DeliveryStatus status);

    @PutMapping("/v1/deliveries/{id}/cancel")
    void cancel(@PathVariable Integer id);

    @GetMapping("/v1/deliveries")
    Object getAll(@RequestParam int page, @RequestParam int size);

}
