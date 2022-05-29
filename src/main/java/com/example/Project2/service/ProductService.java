package com.example.Project2.service;

import com.example.Project2.model.Comment;
import com.example.Project2.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts(){
        return products;
    }

    public boolean addProducts(Product product){
        products.add(product);
        return true;
    }

    public boolean updateProduct(Product product){
        for (int i = 0; i < products.size(); i++) {
            if (product.getId().equals(products.get(i).getId()))
                products.set(i,product);
            return true;
        }
        return false;
    }
    public boolean deleteProduct(String id){
        if(findProductByID(id) == null){
            return false;
        }
        products.remove(findProductByID(id));
        return true;
    }

    public Product findProductByID(String id){
        for (Product product:products) {
            if(id.equals(product.getId())){
                return product;
            }
        }
        return null;
    }
    public ArrayList<Comment> productComments(String productID){
        Product product = findProductByID(productID);
        if(product == null){
            return null;//product not found
        }
        ArrayList<Comment> comments = product.getComment();
        return comments;
    }

    public ArrayList<Product> fiveStarProducts(){
        ArrayList<Product> fiveStarProducts = new ArrayList<>();
        for(int i = 0; i < products.size();i++){
            Product product =products.get(i);
            ArrayList<Comment> comments = product.getComment();
            if(product.getComment() == null){
                continue;
            }
            double rate= 0;
            double rateAvg = 0;
            for(int j = 0; j < comments.size();j++){
                rate+=  product.getComment().get(j).getRate();
            }
            rateAvg = rate/comments.size();
            if(rateAvg == 5){
                fiveStarProducts.add(product);
            }
            if(rateAvg < 5 && fiveStarProducts.contains(product)){
                fiveStarProducts.remove(product);
            }
        }
        return fiveStarProducts;
    }
}
