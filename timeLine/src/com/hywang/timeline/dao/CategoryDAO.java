package com.hywang.timeline.dao;

import java.util.List;

import com.hywang.timeline.entity.Category;


public interface CategoryDAO {
    
    public List<Category> getAllCategories();
    
    public Category getCategoryByID(int ID);
}
