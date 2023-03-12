package com.parceldelivery.msdelivery.controller;

import com.parceldelivery.msdelivery.common.DeliveryStatus;
import com.parceldelivery.msdelivery.common.PaginationRequest;
import com.parceldelivery.msdelivery.model.DeliveryRequestDto;
import com.parceldelivery.msdelivery.model.DeliveryResponseDto;
import com.parceldelivery.msdelivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public Integer create(@RequestBody DeliveryRequestDto deliveryRequestDto) {
        return deliveryService.create(deliveryRequestDto);
    }

    @GetMapping
    public Page<DeliveryResponseDto> getAll(PaginationRequest paginationRequest) {
        return deliveryService.getAll(paginationRequest);
    }

    @GetMapping("/{id}")
    public DeliveryResponseDto getById(@PathVariable Integer id) {
        return deliveryService.getById(id);
    }

    @GetMapping("/couriers/{id}/deliveries")
    public Page<DeliveryResponseDto> getAllByCourierId(@PathVariable Integer id,
                                                       PaginationRequest paginationRequest) {
        return deliveryService.getPageByCourierId(id, paginationRequest);
    }

    //Courier can do this
    @PutMapping("/{id}/change-status")
    public void changeStatus(@PathVariable Integer id, @RequestParam DeliveryStatus status) {
        deliveryService.changeStatus(id, status);
    }

    //Admin can do this
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Integer id) {
        deliveryService.changeStatus(id, DeliveryStatus.CANCELED);
    }

}
