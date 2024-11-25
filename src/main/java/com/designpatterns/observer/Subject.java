package com.designpatterns.observer;


import com.designpatterns.composite.Product;

import java.util.ArrayList;
import java.util.List;

// Subject sınıfı, Observer'ları yönetir ve bildirim gönderir
public class Subject {
    private List<Observer> observers = new ArrayList<>();

    // Observer ekleme metodu
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Observer çıkarma metodu
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Tüm Observer'lara güncelleme bildirimi gönderme metodu
    public void notifyObservers(Product product) {
        for (Observer observer : observers) {
            observer.update(product);
        }
    }
}
