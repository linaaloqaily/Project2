package com.example.Project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor @Data
public class MerchantStock {

    @NotEmpty(message = "id is required")
    @Min(value = 3 ,message = "id should be 3 number")
    private String id;

    @NotEmpty(message = "product id is required")
    @Min(value = 3 ,message = "product id should be 3 number")
    private String productid;

    @NotEmpty(message = "merchant id is required")
    @Min(value = 3 ,message = "merchant id should be 3 number")
    private String merchantid;

    @NotNull(message = "stock is required")
    @Min(value = 10, message = "stock should be 10 or more than at start")
    private Integer stock;


}
