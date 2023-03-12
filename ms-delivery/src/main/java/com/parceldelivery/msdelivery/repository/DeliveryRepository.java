package com.parceldelivery.msdelivery.repository;

import com.parceldelivery.msdelivery.common.DeliveryStatus;
import com.parceldelivery.msdelivery.entity.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {


    @Query(value = "select d from Delivery d where d.courierId = :courierId and d.status = 'ON_COURIER'")
    Optional<Boolean> courierIsAvailable(Integer courierId);

    Page<Delivery> findAllByCourierId(Integer courierId, Pageable pageable);

    @Query(value = "update from Delivery d set d.status = :deliveryStatus where d = :delivery")
    void changeStatus(Delivery delivery, DeliveryStatus deliveryStatus);

}
