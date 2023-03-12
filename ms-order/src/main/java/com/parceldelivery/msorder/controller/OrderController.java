package com.parceldelivery.msorder.controller;

import com.parceldelivery.msorder.common.OrderStatus;
import com.parceldelivery.msorder.common.PaginationRequest;
import com.parceldelivery.msorder.model.LocationRequestDto;
import com.parceldelivery.msorder.model.OrderRequestDto;
import com.parceldelivery.msorder.model.OrderResponseDto;
import com.parceldelivery.msorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public Integer create(@RequestBody @Valid OrderRequestDto requestDto) {
        return orderService.create(requestDto);
    }

    @GetMapping
    public Page<OrderResponseDto> getPage(PaginationRequest paginationRequest) {
        return orderService.getPage(paginationRequest);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getById(@PathVariable Integer id) {
        return orderService.getById(id);
    }

    @GetMapping("/users/{userId}/orders")
    public Page<OrderResponseDto> getPageByUserId(@PathVariable Integer userId, PaginationRequest paginationRequest) {
        return orderService.getPageByUserId(userId, paginationRequest);
    }

    @PutMapping("/{id}/change-status")
    public void changeStatus(@PathVariable Integer id, @RequestParam OrderStatus status) {
        orderService.changeStatus(id, status);
    }

    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Integer id, @RequestParam Integer userId) {
        orderService.cancel(id, userId);
    }

    @PutMapping("/{id}/change-destination")
    public void changeDestination(@PathVariable Integer id, @RequestBody LocationRequestDto requestDto) {
        orderService.changeDestination(id, requestDto);
    }

    @PostMapping("/test")
    public void produce(@RequestParam String message) {
        kafkaTemplate.send("create-order", message);
    }

}
