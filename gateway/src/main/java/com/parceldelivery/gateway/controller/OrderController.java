package com.parceldelivery.gateway.controller;

import com.parceldelivery.gateway.client.PaginationRequest;
import com.parceldelivery.gateway.client.admin.MsAuthAdminClient;
import com.parceldelivery.gateway.client.order.LocationClientRequestDto;
import com.parceldelivery.gateway.client.order.MsOrderClient;
import com.parceldelivery.gateway.client.order.OrderClientRequestDto;
import com.parceldelivery.gateway.client.order.OrderStatus;
import com.parceldelivery.gateway.client.user.MsAuthUserClient;
import com.parceldelivery.gateway.client.user.ProfileClientResponseDto;
import com.parceldelivery.gateway.dto.LocationRequestDto;
import com.parceldelivery.gateway.dto.OrderRequestDto;
import com.parceldelivery.gateway.mapper.OrderMapper;
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
@RequestMapping("/v1/orders")
public class OrderController {

    private final MsAuthAdminClient msAuthAdminClient;
    private final MsAuthUserClient msAuthUserClient;
    private final MsOrderClient msOrderClient;
    private final OrderMapper orderMapper;

    @ApiOperation(value = "Get all parcels",
            notes = "Only admins can see all parcels. You must be login as a ADMIN!")
    @GetMapping
    public Object getPage(PaginationRequest paginationRequest, HttpServletRequest httpServletRequest) {
        msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
        return msOrderClient.getPage(paginationRequest.getPage(), paginationRequest.getSize());
    }

    @ApiOperation(value = "Create a parcel",
            notes = "Only users can create a parcel. You must be login as a USER!")
    @PostMapping
    public Object create(@RequestBody OrderRequestDto orderRequestDto, HttpServletRequest httpServletRequest) {
        ProfileClientResponseDto profileClientResponseDto = msAuthUserClient
                .verify(httpServletRequest.getHeader("Authorization"));

        OrderClientRequestDto orderClientRequestDto = orderMapper.toOrderClientRequestDto(orderRequestDto);
        orderClientRequestDto.setUserId(profileClientResponseDto.getId());

        return msOrderClient.create(orderClientRequestDto);
    }

    @ApiOperation(value = "Change parcel destination",
            notes = "Only users can change parcel destination. You must be login as a USER!")
    @PutMapping("/{id}/change-destination")
    public void changeDestination(@PathVariable Integer id, @RequestBody LocationRequestDto requestDto,
                                  HttpServletRequest httpServletRequest) {
        ProfileClientResponseDto profileClientResponseDto = msAuthUserClient
                .verify(httpServletRequest.getHeader("Authorization"));

        LocationClientRequestDto locationClientRequestDto = orderMapper.toLocationClientRequestDto(requestDto);
        locationClientRequestDto.setUserId(profileClientResponseDto.getId());

        msOrderClient.changeDestination(id, locationClientRequestDto);
    }

    @ApiOperation(value = "Cancel parcel",
            notes = "Only users can change parcel destination. You must be login as a USER!")
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        ProfileClientResponseDto profileClientResponseDto = msAuthUserClient
                .verify(httpServletRequest.getHeader("Authorization"));
        msOrderClient.cancel(id, profileClientResponseDto.getId());
    }

    @ApiOperation(value = "Change parcel status",
            notes = "Only admins can change parcel status. You must be login as a ADMIN!")
    @PutMapping("/{id}/change-status")
    public void changeStatus(@PathVariable Integer id, @RequestParam OrderStatus status,
                             HttpServletRequest httpServletRequest) {
        msAuthAdminClient.verify(httpServletRequest.getHeader("Authorization"));
        msOrderClient.changeStatus(id, status);
    }

    @ApiOperation(value = "Get own parcels",
            notes = "Only users can get own parcels. You must be login as a USER!")
    @GetMapping("/by-user")
    public Object getPageByUser(PaginationRequest paginationRequest,
                                HttpServletRequest httpServletRequest) {
        ProfileClientResponseDto profileClientResponseDto = msAuthUserClient
                .verify(httpServletRequest.getHeader("Authorization"));
        return msOrderClient.getPageByUserId(profileClientResponseDto.getId(),
                paginationRequest.getPage(), paginationRequest.getSize());
    }

}
