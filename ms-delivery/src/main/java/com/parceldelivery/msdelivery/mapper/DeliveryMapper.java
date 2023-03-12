package com.parceldelivery.msdelivery.mapper;


import com.parceldelivery.msdelivery.entity.Delivery;
import com.parceldelivery.msdelivery.model.DeliveryResponseDto;

public class DeliveryMapper {

    public static DeliveryResponseDto toDto(Delivery delivery) {
        DeliveryResponseDto deliveryResponseDto = new DeliveryResponseDto();
        deliveryResponseDto.setId(delivery.getId());
        deliveryResponseDto.setOrderId(delivery.getOrderId());
        deliveryResponseDto.setCourierId(delivery.getCourierId());
        deliveryResponseDto.setStatus(delivery.getStatus());
        return deliveryResponseDto;
    }

}
