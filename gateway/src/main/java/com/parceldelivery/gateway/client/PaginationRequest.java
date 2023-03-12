package com.parceldelivery.gateway.client;

import lombok.Data;

@Data
public class PaginationRequest {

    private int page = 0;
    private int size = 5;

}
