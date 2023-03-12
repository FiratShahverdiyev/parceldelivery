package com.parceldelivery.gateway.client.order;

import lombok.Data;

@Data
public class OrderClientResponseDto {

    private Integer id;
    private String details;
    private String status;
    private Float longitude;
    private Float latitude;
    private Integer userId;

}
