package com.example.Project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor @Data
public class PurchaseHistory {

    @NotEmpty(message = "id is required")
    @Min(value = 3 ,message = "id should be 3 number")
    private String id;

    @NotEmpty(message = "userid is required")
    @Min(value = 3 ,message = "userid should be 3 number")
    private String userid;

    @NotEmpty(message = "productid is required")
    @Min(value = 3 ,message = "productid should be 3 number")
    private String productid;

    @NotNull(message = "price is required")
    @Positive(message = "price should be positive")
    private Integer price;

}
