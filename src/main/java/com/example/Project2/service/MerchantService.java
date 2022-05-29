package com.example.Project2.service;

import com.example.Project2.model.Merchant;

import com.example.Project2.model.MerchantStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {

    ArrayList<Merchant> merchants = new ArrayList<>();
    private final MerchantStockService merchantStockService;

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean addMerchants(Merchant merchant){
        merchants.add(merchant);
        return true;
    }

    public boolean updateMerchants(Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchant.getId().equals(merchants.get(i).getId()))
                merchants.set(i,merchant);
            return true;
        }
        return false;
    }
    public boolean deleteMerchant(String id){
        if(finMerchantByID(id) == null){
            return false;
        }
        merchants.remove(finMerchantByID(id));
        return true;
    }

    public Merchant finMerchantByID(String id){
        for (Merchant merchant:merchants) {
            if(id.equals(merchant.getId())){
                return merchant;
            }
        }
        return null;
    }
    public boolean addStock(String merchantID,Integer stock){
        MerchantStock merchantStock = merchantStockService.finMerchantStockByID(merchantID);
        if(merchantStock == null){
            return false;//merchant not found
        }
        merchantStock.setStock(merchantStock.getStock()+stock);
        return true;
    }
}
