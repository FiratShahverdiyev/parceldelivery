package com.parceldelivery.gateway.client.order;

import com.parceldelivery.gateway.client.PaginationRequest;
import com.parceldelivery.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${client.order.name}", url = "${client.order.url}",
        configuration = FeignClientConfiguration.class)
public interface MsOrderClient {

    @PostMapping("/v1/orders")
    Object create(@RequestBody OrderClientRequestDto requestDto);

    @GetMapping("/v1/orders")
    Object getPage(@RequestParam int page, @RequestParam int size);

    @GetMapping("/v1/orders/{id}")
    Object getById(@PathVariable Integer id);

    @PutMapping("/v1/orders/{id}/change-destination")
    void changeDestination(@PathVariable Integer id, @RequestBody LocationClientRequestDto requestDto);

    @PutMapping("/v1/orders/{id}/change-status")
    void changeStatus(@PathVariable Integer id, @RequestParam OrderStatus status);

    @PutMapping("/v1/orders/{id}/cancel")
    void cancel(@PathVariable Integer id, @RequestParam Integer userId);

    @GetMapping("/v1/orders/users/{userId}/orders")
    Object getPageByUserId(@PathVariable Integer userId, @RequestParam int page, @RequestParam int size);

}
