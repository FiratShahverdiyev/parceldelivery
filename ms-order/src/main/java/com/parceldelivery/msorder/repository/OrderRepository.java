package com.parceldelivery.msorder.repository;


import com.parceldelivery.msorder.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAllByUserId(Integer userId, Pageable pageable);

}
