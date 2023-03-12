package com.parceldelivery.msorder.process;

import com.parceldelivery.msorder.common.OrderStatus;
import com.parceldelivery.msorder.entity.Order;
import com.parceldelivery.msorder.error.ProcessIsNotPossibleException;
import com.parceldelivery.msorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAcceptProcess extends BaseOrderProcess {

    private final OrderRepository orderRepository;

    @Override
    protected void process(Order order) {
        order.setStatus(OrderStatus.ACCEPTED);
        orderRepository.save(order);
    }

    @Override
    protected void checkNextProcessIsPossible(Order order) {
        if (!OrderStatus.IN_PROGRESS.equals(order.getStatus()))
            throw new ProcessIsNotPossibleException("This process is not possible. " +
                    "Contact to developer team. orderId : " + order.getId());
    }

}
