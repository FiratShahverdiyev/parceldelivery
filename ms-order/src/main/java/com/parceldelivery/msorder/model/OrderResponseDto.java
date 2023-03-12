package com.parceldelivery.msorder.model;

import com.parceldelivery.msorder.common.OrderStatus;
import lombok.Data;

@Data
public class OrderResponseDto {

    private Integer id;
    private String details;
    private OrderStatus status;
    private Float longitude;
    private Float latitude;
    private Integer userId;

}
