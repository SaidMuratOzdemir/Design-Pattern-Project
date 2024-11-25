package com.designpatterns.observer;


import com.designpatterns.composite.Product;
import com.designpatterns.dao.ProductDAO;

// StockManager sınıfı, stok değişikliklerini yönetir ve Observer'lara bildirir
public class StockManager extends Subject {

    private ProductDAO productDAO;

    public StockManager() {
        this.productDAO = new ProductDAO();
    }

    // Stok güncelleme işlemi
    public void updateStock(Product product, int quantity) {
        product.updateStock(quantity);
        productDAO.updateProduct(product); // Veritabanında stok güncelle
        notifyObservers(product);
    }
}
