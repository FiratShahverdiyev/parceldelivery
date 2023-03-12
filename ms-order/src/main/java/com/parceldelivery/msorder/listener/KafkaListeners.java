package com.parceldelivery.msorder.listener;

import com.parceldelivery.msorder.common.OrderStatus;
import com.parceldelivery.msorder.entity.Order;
import com.parceldelivery.msorder.error.EntityNotFoundException;
import com.parceldelivery.msorder.process.BaseOrderProcess;
import com.parceldelivery.msorder.process.OrderProcessFactory;
import com.parceldelivery.msorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final OrderRepository orderRepository;
    private final OrderProcessFactory orderProcessFactory;

    @SneakyThrows
    @KafkaListener(topics = "create-delivery", groupId = "delivery-creators")
    protected void createDeliveryListener(String data) {
        Integer orderId = Integer.parseInt(data);
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException(Order.class, orderId));

        BaseOrderProcess baseOrderProcess = orderProcessFactory.create(OrderStatus.ON_DELIVERY);
        baseOrderProcess.tryProcess(order);
    }

}
