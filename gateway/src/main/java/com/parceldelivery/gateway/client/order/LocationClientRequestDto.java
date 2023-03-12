package com.parceldelivery.gateway.client.order;

import lombok.Data;

@Data
public class LocationClientRequestDto {

    private Integer userId;
    private Float longitude;
    private Float latitude;

}
