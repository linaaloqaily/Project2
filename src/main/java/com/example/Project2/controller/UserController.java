package com.example.Project2.controller;

import com.example.Project2.model.*;
import com.example.Project2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<ArrayList<User>> getUsers(){
        return ResponseEntity.status(200).body((userService.getUsers()));
    }
    @PostMapping("/add")
    public ResponseEntity<Api> addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new Api(errors.getFieldError().getDefaultMessage(),400));
        }
        if(!userService.addUser(user)){
            return ResponseEntity.status(500).body(new Api("Server error adding user",500));
        }
        return ResponseEntity.status(200).body(new Api("User added successfully",200));
    }
    @PutMapping("/update")
    public ResponseEntity<Api> updateUser(@RequestBody @Valid User user,Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new Api(errors.getFieldError().getDefaultMessage(),400));
        }
        if(userService.updateUser(user)){
            return ResponseEntity.status(200).body(new Api("User updated",200));
        }
        return ResponseEntity.status(400).body(new Api("User not found",400));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Api> deleteUser(@PathVariable String id){
        if(userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new Api("User deleted",200));
        }
        return ResponseEntity.status(400).body(new Api("User not found",400));
    }
    @PutMapping("/addtocart/{userID}/{productID}")
    public ResponseEntity<Api> addTOCart(@PathVariable String userID, @PathVariable String productID){
        Integer addStatus = userService.addToCart(userID,productID);
        switch (addStatus){
            case -1:
                return ResponseEntity.status(400).body(new Api("User not found",400));
            case 0:
                return ResponseEntity.status(400).body(new Api("Product not found",400));
            case 1:
                return ResponseEntity.status(200).body(new Api("Product added to cart successfully!",200));
            default:
                return ResponseEntity.status(500).body(new Api("Server error",500));
        }
    }
    @PutMapping("/removeitem/{userID}/{productID}")
    public ResponseEntity<Api> removeFromCart(@PathVariable String userID, @PathVariable String productID){
        Integer removeStatus = userService.removeFromCart(userID,productID);
        switch (removeStatus){
            case -1:
                return ResponseEntity.status(400).body(new Api("User not found",400));
            case 0:
                return ResponseEntity.status(400).body(new Api("Product not found",400));
            case 1:
                return ResponseEntity.status(200).body(new Api("Product deleted from the cart",200));
            case 2:
                return ResponseEntity.status(400).body(new Api("Product was not found in the cart",400));
            default:
                return ResponseEntity.status(500).body(new Api("Server error",500));
        }
    }
    @PutMapping("/buywithoutcart/{userID}/{productID}/{merchantID}")
    public ResponseEntity<Api> buyWithoutCart(@PathVariable String userID,@PathVariable String productID,@PathVariable String merchantID){
        Integer buyStatus = userService.buyWithoutCart(userID,productID,merchantID);
        switch (buyStatus){
            case -1:
                return ResponseEntity.status(400).body(new Api("Merchant not found",400));
            case 0:
                return ResponseEntity.status(400).body(new Api("Merchant doesn't sell this product",400));
            case 1:
                return ResponseEntity.status(400).body(new Api("Out of stock",400));
            case 2:
                return ResponseEntity.status(400).body(new Api("User doesn't have enough balance",400));
            case 3:
                return ResponseEntity.status(200).body(new Api("Purchase completed",200));
            default:
                return ResponseEntity.status(500).body(new Api("Server error",500));
        }
    }
    @PutMapping("/buywithcart")
    public ResponseEntity<Api> buyFromCart(@RequestBody Cart cart){
        Integer buyStatus = userService.buyWithCart(cart);
        switch (buyStatus){
            case -4:
                return ResponseEntity.status(400).body(new Api("User not found",400));
            case -3:
                return ResponseEntity.status(400).body(new Api("Cart not found",400));
            case -2:
                return ResponseEntity.status(400).body(new Api("Cart is empty",400));
            case -1:
                return ResponseEntity.status(400).body(new Api("Out of stock",400));
            case 0:
                return ResponseEntity.status(400).body(new Api("User doesn't have enough balance",400));
            case 1:
                return ResponseEntity.status(200).body(new Api("Purchase completed",200));
            default:
                return ResponseEntity.status(500).body(new Api("Server error",500));
        }

    }
    @PostMapping("/postcomment/{userID}/{productID}")
    public ResponseEntity<Api> postComment(@PathVariable String userID,@PathVariable String productID,@RequestBody Comment comment){
        if(userService.postComment(userID,productID,comment)){
            return ResponseEntity.status(200).body(new Api("Comment posted",200));
        }
        return ResponseEntity.status(400).body(new Api("User have to purchase the product to post a comment",400));
    }
    @GetMapping("/userhistory/{userID}")
    public ResponseEntity<ArrayList<PurchaseHistory>> userPurchaseHistory(@PathVariable String userID){
        return ResponseEntity.status(200).body(userService.userPurchaseHistory(userID));
    }
}
