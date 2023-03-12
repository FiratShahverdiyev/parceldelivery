package com.parceldelivery.gateway.client.order;

import lombok.Data;

@Data
public class OrderClientRequestDto {

    private String details;
    private Integer userId;
    private Float longitude;
    private Float latitude;

}
