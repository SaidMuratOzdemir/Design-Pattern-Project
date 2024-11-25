package com.designpatterns;

import com.designpatterns.alerts.AlertSystem;
import com.designpatterns.composite.Category;
import com.designpatterns.composite.Product;
import com.designpatterns.dao.CategoryDAO;
import com.designpatterns.dao.ProductDAO;
import com.designpatterns.factory.CategoryFactory;
import com.designpatterns.factory.ProductFactory;
import com.designpatterns.observer.StockManager;

public class InventoryManagementSystem {

    public static void main(String[] args) {
        // Factory kullanarak kategoriler ve ürünler oluşturma
        CategoryFactory categoryFactory = new CategoryFactory();
        ProductFactory productFactory = new ProductFactory();

        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();

        // Kategorileri veritabanına ekleme
        Category electronics = categoryFactory.createCategory("Elektronik");
        if (categoryDAO.getCategoryByName(electronics.getName()) == null) {
            categoryDAO.addCategory(electronics); // Veritabanına ekleme
        }

        Category phones = categoryFactory.createCategory("Telefonlar");
        phones.setParent(electronics);
        if (categoryDAO.getCategoryByName(phones.getName()) == null) {
            categoryDAO.addCategory(phones); // Veritabanına ekleme
        }

        // Ürünleri veritabanına ekleme
        Product iphone = productFactory.createProduct("P001", "iPhone 14", 50);
        iphone.setCategory(phones);
        if (productDAO.getProductByProductId(iphone.getProductId()) == null) {
            productDAO.addProduct(iphone); // Ürünü veritabanına ekleme
        }

        // Observer ve Alert sistemini kurma
        StockManager stockManager = new StockManager();
        AlertSystem alertSystem = new AlertSystem(10); // Minimum stok seviyesi 10
        stockManager.addObserver(alertSystem);

        // Stok güncelleme ve bildirim
        stockManager.updateStock(iphone, -45); // Stok 5'e düşecek

        // Kategori ve ürünleri görüntüleme (Veritabanından çekerek)
        Category fetchedElectronics = categoryDAO.getCategoryById(electronics.getId());
        if (fetchedElectronics != null) {
            fetchedElectronics.display("");
        }
    }
}
