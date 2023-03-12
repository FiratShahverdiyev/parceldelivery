package com.parceldelivery.msorder.common;

import lombok.Data;

import javax.validation.constraints.Max;


@Data
public class PaginationRequest {

    private int page = 0;
    @Max(25)
    private int size = 5;

}
