package com.designpatterns.alerts;

import com.designpatterns.composite.Product;
import com.designpatterns.observer.Observer;

// AlertSystem, stok seviyeleri belirli eşiklere ulaştığında uyarılar oluşturur
public class AlertSystem implements Observer {

    private int minStockLevel;

    public AlertSystem(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    @Override
    public void update(Product product) {
        if (product.getStock() < minStockLevel) {
            // Uyarı mekanizmasını tetikle (örn. loglama veya e-posta gönderme)
            System.out.println("ALERT: Stok seviyesi düşük! Ürün: " + product.getName() + " | Stok: " + product.getStock());
        }
    }
}

