package com.parceldelivery.msorder.entity;

import com.parceldelivery.msorder.common.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String details; //You can use whatever you want instead of this field. Depend your business logic

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Location destination;

    private Integer userId;

}
