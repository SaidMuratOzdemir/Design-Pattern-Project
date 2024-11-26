package com.designpatterns.factory;


import com.designpatterns.composite.Category;

public class CategoryFactory {
    public Category createCategory(String name) {
        return new Category(name);
    }
}
