package com.designpatterns.factory;


import com.designpatterns.composite.Product;
import com.designpatterns.dao.ProductDAO;

public class ProductFactory {
    private ProductDAO productDAO;

    public ProductFactory() {
        this.productDAO = new ProductDAO();
    }

    // Ürün oluşturma metodu ve veritabanına ekleme
    public Product createProduct(String productId, String name, int stock) {
        Product product = new Product(productId, name, stock);
        productDAO.addProduct(product);
        return product;
    }
}
