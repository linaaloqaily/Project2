package com.example.Project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;

@AllArgsConstructor @Data
public class Product {

    @NotEmpty(message = "id is required")
    @Min(value = 3 ,message = "id should be 3 number")
    private String id;

    @NotEmpty(message = "name is required")
    @Min(value = 3 ,message = "name should be 3 character or more than")
    private String name;

    @NotNull(message = "price is required")
    @Positive(message = "price should be positive")
    private Integer price;

    @NotEmpty(message = "category id is required")
    @Min(value = 3 ,message = "category id should be 3 character")
    private String categoryID;

    private ArrayList<Comment> comment;


}
