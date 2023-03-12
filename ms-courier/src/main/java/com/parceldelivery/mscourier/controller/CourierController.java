package com.parceldelivery.mscourier.controller;

import com.parceldelivery.mscourier.entity.Location;
import com.parceldelivery.mscourier.model.UserResponseDto;
import com.parceldelivery.mscourier.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @GetMapping("/{id}/last-location")
    public Location getLastLocationByUserId(@PathVariable Integer id) {
        return courierService.getLastLocation(id);
    }

    @GetMapping
    public Page<UserResponseDto> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return courierService.getAll(page, size);
    }

}
