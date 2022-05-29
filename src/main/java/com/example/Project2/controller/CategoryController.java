package com.example.Project2.controller;


import com.example.Project2.model.Api;
import com.example.Project2.model.Category;
import com.example.Project2.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<ArrayList<Category>> getCategory(){
        return ResponseEntity.status(200).body((categoryService.getCategory()));
    }
    @PostMapping("/add")
    public ResponseEntity<Api> addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new Api(errors.getFieldError().getDefaultMessage(),400));
        }
        if(!categoryService.addCategory(category)){
            return ResponseEntity.status(500).body(new Api("Server error adding category",500));
        }
        return ResponseEntity.status(200).body(new Api("Category added successfully",200));
    }
    @PutMapping("/update")
    public ResponseEntity<Api> updateCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new Api(errors.getFieldError().getDefaultMessage(),400));
        }
        if(categoryService.updateCategory(category)){
            return ResponseEntity.status(200).body(new Api("Category updated",200));
        }
        return ResponseEntity.status(400).body(new Api("Category not found",400));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Api> deleteCategory(@PathVariable String id){
        if(categoryService.deleteCategory(id)){
            return ResponseEntity.status(200).body(new Api("Category deleted",200));
        }
        return ResponseEntity.status(400).body(new Api("Category not found",400));
    }
}