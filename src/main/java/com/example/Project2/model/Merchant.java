package com.example.Project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor @Data
public class Merchant {

    @NotEmpty(message = "id is required")
    @Min(value = 3 ,message = "id should be 3 number")
    private String id;

    @NotEmpty(message = "name is required")
    @Min(value = 3 ,message = "name should be 3 character or more than")
    private String name;
}
