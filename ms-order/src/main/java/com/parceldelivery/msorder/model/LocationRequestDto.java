package com.parceldelivery.msorder.model;

import lombok.Data;

@Data
public class LocationRequestDto {

    private Integer userId;
    private Float longitude;
    private Float latitude;

}
