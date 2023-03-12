package com.parceldelivery.mscourier.model;

import lombok.Data;

@Data
public class UpdateCourierStatusRequestDto {

    private Integer courierId;
    private boolean available;

}
