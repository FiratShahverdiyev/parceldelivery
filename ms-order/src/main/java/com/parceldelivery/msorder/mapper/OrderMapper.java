package com.parceldelivery.msorder.mapper;

import com.parceldelivery.msorder.entity.Location;
import com.parceldelivery.msorder.entity.Order;
import com.parceldelivery.msorder.model.LocationRequestDto;
import com.parceldelivery.msorder.model.OrderRequestDto;
import com.parceldelivery.msorder.model.OrderResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDto requestDto) {
        Order order = new Order();
        order.setDetails(requestDto.getDetails());
        order.setDestination(new Location(requestDto.getLongitude(), requestDto.getLatitude()));
        order.setUserId(requestDto.getUserId());
        return order;
    }

    public OrderResponseDto toResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        Location destination = order.getDestination();
        orderResponseDto.setLatitude(destination.getLatitude());
        orderResponseDto.setLongitude(destination.getLongitude());
        orderResponseDto.setDetails(order.getDetails());
        orderResponseDto.setUserId(order.getUserId());
        orderResponseDto.setId(order.getId());
        orderResponseDto.setStatus(order.getStatus());
        return orderResponseDto;
    }

    public void updateDestination(Order order, LocationRequestDto requestDto) {
        order.setDestination(new Location(requestDto.getLongitude(), requestDto.getLatitude()));
    }

}
