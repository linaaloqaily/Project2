package com.example.Project2.controller;

import com.example.Project2.model.Api;
import com.example.Project2.model.MerchantStock;
import com.example.Project2.service.MerchantStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;
    @GetMapping
    public ResponseEntity<ArrayList<MerchantStock>> getMerchantStocks(){
        return ResponseEntity.status(200).body((merchantStockService.getMerchantStocks()));
    }
    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new Api(errors.getFieldError().getDefaultMessage(),400));
        }
        if(!merchantStockService.addMerchantStocks(merchantStock)){
            return ResponseEntity.status(500).body(new Api("Server error adding a Merchant Stock",500));
        }
        return ResponseEntity.status(200).body(new Api("Merchant Stock added successfully",200));
    }
    @PutMapping("/update")
    public ResponseEntity<Api> updateMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new Api(errors.getFieldError().getDefaultMessage(),400));
        }
        if(merchantStockService.updateMerchantStocks(merchantStock)){
            return ResponseEntity.status(200).body(new Api("Merchant Stock updated",200));
        }
        return ResponseEntity.status(400).body(new Api("Merchant Stock not found",400));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Api> deleteMerchantStock(@PathVariable String id){
        if(merchantStockService.deleteMerchantStock(id)){
            return ResponseEntity.status(200).body(new Api("Merchant Stock deleted",200));
        }
        return ResponseEntity.status(400).body(new Api("Merchant Stock not found",400));
    }

}
