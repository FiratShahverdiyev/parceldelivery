package com.parceldelivery.gateway.error;

public class FeignException extends RuntimeException {

    public FeignException(String message) {
        super(message);
    }

}
