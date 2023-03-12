package com.parceldelivery.msdelivery.model;

import com.parceldelivery.msdelivery.common.DeliveryStatus;
import lombok.Data;

@Data
public class DeliveryResponseDto {

    private Integer id;
    private Integer orderId;
    private Integer courierId;
    private DeliveryStatus status;

}
