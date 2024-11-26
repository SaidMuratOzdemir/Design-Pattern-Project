package com.designpatterns.alerts;

import com.designpatterns.composite.Product;
import com.designpatterns.observer.Observer;

public class AlertSystem implements Observer {

    private int minStockLevel;

    public AlertSystem(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    @Override
    public void update(Product product) {
        if (product.getStock() < minStockLevel) {
            System.out.println("ALERT: Stok seviyesi düşük! Ürün: " + product.getName() + " | Stok: " + product.getStock());
        }
    }
}

