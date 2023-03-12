package com.parceldelivery.msorder.process;

import com.parceldelivery.msorder.common.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProcessFactory {

    private final OrderCancelProcess orderCancelProcess;
    private final OrderInProgressProcess orderInProgressProcess;
    private final OrderAcceptProcess orderAcceptProcess;
    private final OrderOnDeliveryProcess orderOnDeliveryProcess;
    private final OrderDeliveredProcess orderDeliveredProcess;

    public BaseOrderProcess create(OrderStatus status) {
        return switch (status) {
            case CANCELED -> orderCancelProcess;
            case IN_PROGRESS -> orderInProgressProcess;
            case ACCEPTED -> orderAcceptProcess;
            case ON_DELIVERY -> orderOnDeliveryProcess;
            case DELIVERED -> orderDeliveredProcess;
        };
    }

}
