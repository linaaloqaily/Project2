package com.example.Project2.service;

import com.example.Project2.model.Comment;
import com.example.Project2.model.PurchaseHistory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PurchaseHistoryService {

    ArrayList<PurchaseHistory> purchaseHistories = new ArrayList<>();

    public ArrayList<PurchaseHistory> getPurchaseHistories(){
        return purchaseHistories;
    }
    public boolean addPurchaseHistory(PurchaseHistory purchaseHistory){
        purchaseHistories.add(purchaseHistory);
        return true;
    }
    public boolean checkUserProducts(String userID,String productID){
        for(int i = 0; i < purchaseHistories.size(); i++){//check if user bought the product or not
            if(purchaseHistories.get(i).getUserid().equals(userID) && purchaseHistories.get(i).getProductid().equals(productID)){
                return true;
            }
        }
        return false;
    }


}
