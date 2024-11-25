package com.designpatterns.factory;


import com.designpatterns.composite.Category;
import com.designpatterns.dao.CategoryDAO;

public class CategoryFactory {
    private CategoryDAO categoryDAO;

    public CategoryFactory() {
        this.categoryDAO = new CategoryDAO();
    }

    // Kategori oluşturma metodu ve veritabanına ekleme
    public Category createCategory(String name) {
        Category category = new Category(name);
        categoryDAO.addCategory(category);
        return category;
    }
}
