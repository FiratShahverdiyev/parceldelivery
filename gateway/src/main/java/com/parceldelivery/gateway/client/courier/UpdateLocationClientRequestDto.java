package com.parceldelivery.gateway.client.courier;

import lombok.Data;

@Data
public class UpdateLocationClientRequestDto {

    private Integer courierId;
    private Float longitude;
    private Float latitude;

}
