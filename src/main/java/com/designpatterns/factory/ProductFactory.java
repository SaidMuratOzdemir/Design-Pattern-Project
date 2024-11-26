package com.designpatterns.factory;

import com.designpatterns.composite.Product;

public class ProductFactory {
    public Product createProduct(String productId, String name, int stock) {
        return new Product(productId, name, stock);
    }
}

