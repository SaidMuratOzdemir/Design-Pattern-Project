package com.designpatterns.observer;


import com.designpatterns.composite.Product;
import com.designpatterns.dao.ProductDAO;

// StockManager sınıfı, stok değişikliklerini yönetir ve Observer'lara bildirir
public class StockManager extends Subject {

    private ProductDAO productDAO;

    public StockManager() {
        this.productDAO = new ProductDAO();
    }

    public void updateStock(Product product, int quantity) {
        product.setStock(quantity);
        productDAO.updateProduct(product);
        notifyObservers(product);
    }
}
