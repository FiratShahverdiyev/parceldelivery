package com.parceldelivery.mscourier.model;

import lombok.Data;

@Data
public class UserResponseDto {

    private Integer id;
    private String fullName;
    private String username;
    private boolean available;

}
