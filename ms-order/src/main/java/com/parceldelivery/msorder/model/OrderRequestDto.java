package com.parceldelivery.msorder.model;

import lombok.Data;

@Data
public class OrderRequestDto {

    private String details;
    private Integer userId;
    private Float longitude;
    private Float latitude;

}
