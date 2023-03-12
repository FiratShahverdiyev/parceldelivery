package com.parceldelivery.msdelivery.process;

import com.parceldelivery.msdelivery.common.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryProcessFactory {

    private final DeliveryCancelProcess deliveryCancelProcess;
    private final DeliveryOnCourierProcess deliveryOnCourierProcess;
    private final DeliveryDeliveredProcess deliveryDeliveredProcess;

    public BaseDeliveryProcess create(DeliveryStatus status) {
        return switch (status) {
            case CANCELED -> deliveryCancelProcess;
            case ON_COURIER -> deliveryOnCourierProcess;
            case DELIVERED -> deliveryDeliveredProcess;
        };
    }

}
