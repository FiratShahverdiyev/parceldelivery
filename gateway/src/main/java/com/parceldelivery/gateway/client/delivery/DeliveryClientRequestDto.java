package com.parceldelivery.gateway.client.delivery;

import lombok.Data;

@Data
public class DeliveryClientRequestDto {

    private Integer courierId;
    private Integer orderId;

}
