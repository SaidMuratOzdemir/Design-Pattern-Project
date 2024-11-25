package com.designpatterns.observer;


import com.designpatterns.composite.Product;

// Observer arayüzü, stok değişikliklerini dinleyen varlıkları tanımlar
public interface Observer {
    void update(Product product);
}

