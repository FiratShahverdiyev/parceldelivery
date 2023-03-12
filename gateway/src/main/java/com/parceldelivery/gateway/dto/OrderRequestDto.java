package com.parceldelivery.gateway.dto;

import lombok.Data;

@Data
public class OrderRequestDto {

    private String details;
    private Float longitude;
    private Float latitude;

}
