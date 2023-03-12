package com.parceldelivery.gateway.controller;

import com.parceldelivery.gateway.client.PaginationRequest;
import com.parceldelivery.gateway.client.admin.MsAuthAdminClient;
import com.parceldelivery.gateway.client.courier.MsCourierClient;
import com.parceldelivery.gateway.client.courier.ProfileClientResponseDto;
import com.parceldelivery.gateway.client.delivery.DeliveryClientRequestDto;
import com.parceldelivery.gateway.client.delivery.DeliveryStatus;
import com.parceldelivery.gateway.client.delivery.MsDeliveryClient;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/deliveries")
public class DeliveryController {

    private final MsAuthAdminClient msAuthAdminClient;
    private final MsDeliveryClient msDeliveryClient;
    private final MsCourierClient msCourierClient;

    @ApiOperation(value = "Create delivery for parcel",
            notes = "Only admins can create delivery for parcel. You must be login as a ADMIN!")
    @PostMapping
    public Object create(@RequestBody DeliveryClientRequestDto requestDto, HttpServletRequest httpServletRequest) {
        msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
        return msDeliveryClient.create(requestDto);
    }

    @ApiOperation(value = "Get all own deliveries by courier",
            notes = "Only couriers can get own deliveries. You must be login as a COURIER!")
    @GetMapping("/by-courier")
    public Object getDeliveriesByCourier(PaginationRequest paginationRequest,
                                         HttpServletRequest httpServletRequest) {
        ProfileClientResponseDto profileClientResponseDto = msCourierClient
                .verify(httpServletRequest.getHeader("Authorization"));
        return msDeliveryClient.getDeliveriesByCourierId(profileClientResponseDto.getId(),
                paginationRequest.getPage(), paginationRequest.getSize());
    }

    @ApiOperation(value = "Get deliveries",
            notes = "Only admins can get all deliveries. You must be login as a ADMIN!")
    @GetMapping
    public Object getDeliveries(PaginationRequest paginationRequest,
                                HttpServletRequest httpServletRequest) {
        msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
        return msDeliveryClient.getAll(paginationRequest.getPage(), paginationRequest.getSize());
    }

    @ApiOperation(value = "Change delivery status by id and delivery status",
            notes = "Only couriers can change delivery status to ON_COURIER or DELIVERED. You must be login as a COURIER!")
    @PutMapping("/{id}/change-status")
    public void changeStatus(@PathVariable Integer id, @RequestParam DeliveryStatus status,
                             HttpServletRequest httpServletRequest) {
        msCourierClient.verify(httpServletRequest.getHeader("Authorization"));
        msDeliveryClient.changeStatus(id, status);
    }

    @ApiOperation(value = "Cancel delivery",
            notes = "Only admins can cancel delivery. You must be login as a ADMIN!")
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Integer id,
                       HttpServletRequest httpServletRequest) {
        msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
        msDeliveryClient.cancel(id);
    }

}
