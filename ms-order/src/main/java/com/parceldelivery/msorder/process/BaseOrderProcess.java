package com.parceldelivery.msorder.process;

import com.parceldelivery.msorder.entity.Order;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseOrderProcess {

    @Transactional
    public void tryProcess(Order order) {
        checkNextProcessIsPossible(order);
        process(order);
    }

    protected abstract void process(Order order);

    protected abstract void checkNextProcessIsPossible(Order order);

}
