package com.parceldelivery.msorder.process;

import com.parceldelivery.msorder.common.OrderStatus;
import com.parceldelivery.msorder.entity.Order;
import com.parceldelivery.msorder.error.ProcessIsNotPossibleException;
import com.parceldelivery.msorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class OrderInProgressProcess extends BaseOrderProcess {

    private final OrderRepository orderRepository;

    @Override
    protected void process(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);
    }

    @Override
    protected void checkNextProcessIsPossible(Order order) {
        if (!Objects.isNull(order.getStatus()))
            throw new ProcessIsNotPossibleException("This process is not possible. " +
                    "Contact to developer team. orderId : " + order.getId());
    }

}
