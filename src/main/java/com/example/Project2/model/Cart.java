package com.example.Project2.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Data
public class Cart {
    @NotEmpty(message = "id is required")
    @Min(value = 3 ,message = "id should be 3 number")
    private String id;
    @NotEmpty(message = "user id is required")
    @Min(value = 3 ,message = "user id should be 3 number")
    private String userid;

    private ArrayList<Product> cartProducts;


    public Cart(String id, String userid, ArrayList<Product> products) {
        this.id = id;
        this.userid = userid;
        this.cartProducts = new ArrayList<>();
    }
}
