package com.example.Project2.controller;

import com.example.Project2.model.Api;
import com.example.Project2.model.Comment;
import com.example.Project2.model.Product;
import com.example.Project2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @GetMapping
    public ResponseEntity<ArrayList<Product>> getProducts(){
        return ResponseEntity.status(200).body((productService.getProducts()));
    }
    @PostMapping("/add")
    public ResponseEntity<Api> addProduct(@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new Api(errors.getFieldError().getDefaultMessage(),400));
        }
        if(!productService.addProducts(product)){
            return ResponseEntity.status(500).body(new Api("Server error adding a product",500));
        }
        return ResponseEntity.status(200).body(new Api("Product added successfully",200));
    }
    @PutMapping("/update")
    public ResponseEntity<Api> updateProduct(@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new Api(errors.getFieldError().getDefaultMessage(),400));
        }
        if(productService.updateProduct(product)){
            return ResponseEntity.status(200).body(new Api("Product updated",200));
        }
        return ResponseEntity.status(400).body(new Api("Product not found",400));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Api> deleteProduct(@PathVariable String id){
        if(productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new Api("Product deleted",200));
        }
        return ResponseEntity.status(400).body(new Api("Product not found",400));
    }
    @GetMapping("/productcomments/{productID}")
    public ResponseEntity<ArrayList<Comment>> productComments(@PathVariable String  productID){
        return ResponseEntity.status(200).body(productService.productComments(productID));
    }
    @GetMapping("/fivestarproducts")
    public ResponseEntity <ArrayList<Product>> fiveStarProducts(){
        return ResponseEntity.status(200).body(productService.fiveStarProducts());
    }


}
