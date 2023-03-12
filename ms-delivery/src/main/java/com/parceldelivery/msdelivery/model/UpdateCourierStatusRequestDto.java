package com.parceldelivery.msdelivery.model;

import lombok.Data;

@Data
public class UpdateCourierStatusRequestDto {

    private Integer courierId;
    private boolean available;

}
