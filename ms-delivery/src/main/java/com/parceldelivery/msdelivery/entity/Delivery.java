package com.parceldelivery.msdelivery.entity;

import com.parceldelivery.msdelivery.common.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer orderId;
    private Integer courierId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
