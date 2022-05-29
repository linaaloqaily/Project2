package com.example.Project2.service;

import com.example.Project2.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategory(){
        return categories;
    }

    public boolean addCategory(Category category){
        categories.add(category);
        return true;
    }

    public boolean updateCategory(Category category){
        for (int i = 0; i < categories.size(); i++) {
            if (category.getId().equals(categories.get(i).getId()))
                categories.set(i,category);
            return true;
        }
        return false;
    }
    public boolean deleteCategory(String id){
        if(findProductByID(id) == null){
            return false;
        }
        categories.remove(findProductByID(id));
        return true;
    }

    public Category findProductByID(String id){
        for (Category category:categories) {
            if(id.equals(category.getId())){
                return category;
            }
        }
        return null;
    }
}
