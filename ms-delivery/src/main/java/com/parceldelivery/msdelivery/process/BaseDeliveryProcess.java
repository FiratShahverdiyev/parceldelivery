package com.parceldelivery.msdelivery.process;

import com.parceldelivery.msdelivery.entity.Delivery;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseDeliveryProcess {

    @Transactional
    public void tryProcess(Delivery delivery) {
        checkNextProcessIsPossible(delivery);
        process(delivery);
    }

    protected abstract void process(Delivery delivery);

    protected abstract void checkNextProcessIsPossible(Delivery delivery);

}
