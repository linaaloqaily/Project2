package com.example.Project2.service;

import com.example.Project2.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor

public class UserService {
    ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;
    private final CartService cartService;
    private ArrayList<Product> cartProduct = new ArrayList<>();
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    private final PurchaseHistoryService purchaseHistoryService;

    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean addUser(User user){
        users.add(user);
        Cart userCart = new Cart(user.getId(),user.getId(),cartProduct);
        cartService.addCart(userCart);
        return true;
    }

    public boolean updateUser(User user){
        for (int i = 0; i < users.size(); i++) {
            if (user.getId().equals(users.get(i).getId()))
                users.set(i,user);
            return true;
        }
        return false;
    }
    public boolean deleteUser(String id){
        if(findUserByID(id) == null){
            return false;
        }
        users.remove(findUserByID(id));
        return true;
    }

    public User findUserByID(String id){
        for (User user:users) {
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
    public Integer addToCart(String userID,String productID){
        if(findUserByID(userID)==null){
            return -1;//user not found
        }
        Product productFound = productService.findProductByID(productID);
        if(productFound == null){
            return 0;//product not found
        }
        Cart userCart = cartService.findCartByID(userID);
        ArrayList<Product> oldCart = userCart.getCartProducts();
        if(userCart.getCartProducts() == null){
            oldCart = new ArrayList<>();
            oldCart.add(productFound);
            userCart.setCartProducts(oldCart);
            return 1;

        }
        oldCart.add(productFound);
        userCart.setCartProducts(oldCart);
        return 1;//product added to the cart

    }
    public Integer removeFromCart(String userID,String productID){
        if(findUserByID(userID)==null){
            return -1;//user not found
        }
        Product productFound = productService.findProductByID(productID);
        if(productFound == null){
            return 0;//product not found
        }
        Cart userCart = cartService.findCartByID(userID);
        ArrayList<Product> oldCart = userCart.getCartProducts();
        for(int i = 0; i < oldCart.size();i++){
            if(oldCart.get(i).getId().equals(productID)){
                oldCart.remove(productFound);
                return 1;//product was found in the cart and deleted
            }
        }
        return 2;//product was not in the cart

    }
    public Integer buyWithoutCart(String userID,String productID,String merchantID){
        Merchant merchant = merchantService.finMerchantByID(merchantID);
        if(merchant == null){
            return -1;//merchant not found
        }
        MerchantStock merchantStock = merchantStockService.finMerchantStockByID(merchantID);
        if(!merchantStock.getProductid().equals(productID)){
            return 0;//merchant doesn't sell this product
        }
        if(merchantStock.getStock() <= 0){
            return 1;//out of stock
        }
        Product product = productService.findProductByID(productID);
        User user = findUserByID(userID);
        if(user.getBalance()< product.getPrice()){
            return 2;//user doesn't have enough balance
        }
        merchantStock.setStock(merchantStock.getStock()-1);
        user.setBalance(user.getBalance() - product.getPrice());
        Integer purchaseID = purchaseHistoryService.getPurchaseHistories().size()+1;
        String newPurchaseID = "00"+purchaseID;
        PurchaseHistory purchaseHistory = new PurchaseHistory(newPurchaseID,userID,productID,product.getPrice());
        purchaseHistoryService.addPurchaseHistory(purchaseHistory);
        return 3;//purchase completed
    }

    public Integer buyWithCart(Cart cart1){
        String cartID = cart1.getId();
        User user = findUserByID(cart1.getUserid());
        if(user == null){
            return -4;//user not found
        }
        Cart cart = cartService.findCartByID(cartID);
        if(cart == null){
            return -3;//cart not found
        }
        if(cart.getCartProducts() == null){
            return -2;//cart has no products
        }
        MerchantStock merchantStock;
        Integer totalPrice = 0;
        for(int i = 0; i < cart.getCartProducts().size(); i++){//checks if product is out of stock or not
            Product products = cart.getCartProducts().get(i);
            merchantStock = merchantStockService.finMerchantStockByProductID(products.getId());
            if(merchantStock.getStock() <= 0){
                return -1;//out of stock
            }
        }
        for(int i = 0; i < cart.getCartProducts().size();i++){//get total price of the cart
            Product products = cart.getCartProducts().get(i);
            totalPrice += products.getPrice();
        }
        if(totalPrice > user.getBalance()){
            return 0;//balance is not enough
        }
        for(int i = 0; i < cart.getCartProducts().size();i++){//reduce stock from merchant stock and adding to the purchase history
            Product products = cart.getCartProducts().get(i);
            merchantStock = merchantStockService.finMerchantStockByProductID(products.getId());
            merchantStock.setStock(merchantStock.getStock()-1);
            Integer purchaseID = purchaseHistoryService.getPurchaseHistories().size()+1;
            String newPurchaseID = "00"+purchaseID;
            PurchaseHistory purchaseHistory = new PurchaseHistory(newPurchaseID,user.getId(),products.getId(),products.getPrice());
            purchaseHistoryService.addPurchaseHistory(purchaseHistory);
        }
        user.setBalance(user.getBalance()- totalPrice);//deduct the amount from the user
        return 1;//purchase completed

    }
    public Boolean postComment(String userID, String productID,Comment comment){

        if(!purchaseHistoryService.checkUserProducts(userID,productID)){
            return false;//user didn't buy the product
        }
        Product product = productService.findProductByID(productID);

        ArrayList<Comment> productComments = product.getComment();
        if(product.getComment() == null){
            productComments = new ArrayList<>();
            productComments.add(comment);
            product.setComment(productComments);
            return true;
        }
        productComments.add(comment);
        product.setComment(productComments);
        return true;
    }
    public ArrayList<PurchaseHistory> userPurchaseHistory(String userID){
        ArrayList<PurchaseHistory> userHistory = new ArrayList<>();
        for(int i = 0; i < purchaseHistoryService.getPurchaseHistories().size();i++){
            PurchaseHistory purchaseHistory = purchaseHistoryService.getPurchaseHistories().get(i);
            if(purchaseHistory.getUserid().equals(userID)){
                userHistory.add(purchaseHistory);
            }
        }
        return userHistory;
    }
}
