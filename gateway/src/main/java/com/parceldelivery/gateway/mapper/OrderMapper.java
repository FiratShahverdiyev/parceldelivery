package com.parceldelivery.gateway.mapper;

import com.parceldelivery.gateway.client.order.LocationClientRequestDto;
import com.parceldelivery.gateway.client.order.OrderClientRequestDto;
import com.parceldelivery.gateway.dto.LocationRequestDto;
import com.parceldelivery.gateway.dto.OrderRequestDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderClientRequestDto toOrderClientRequestDto(OrderRequestDto orderRequestDto) {
        OrderClientRequestDto orderClientRequestDto = new OrderClientRequestDto();
        orderClientRequestDto.setDetails(orderRequestDto.getDetails());
        orderClientRequestDto.setLongitude(orderRequestDto.getLongitude());
        orderClientRequestDto.setLatitude(orderRequestDto.getLatitude());
        return orderClientRequestDto;
    }

    public LocationClientRequestDto toLocationClientRequestDto(LocationRequestDto locationRequestDto) {
        LocationClientRequestDto locationClientRequestDto = new LocationClientRequestDto();
        locationClientRequestDto.setLatitude(locationRequestDto.getLatitude());
        locationClientRequestDto.setLongitude(locationRequestDto.getLongitude());
        return locationClientRequestDto;
    }

}
