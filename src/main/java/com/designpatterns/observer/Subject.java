package com.designpatterns.observer;


import com.designpatterns.composite.Product;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Product product) {
        for (Observer observer : observers) {
            observer.update(product);
        }
    }
}
