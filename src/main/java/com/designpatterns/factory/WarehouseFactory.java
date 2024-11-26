package com.designpatterns.factory;

import com.designpatterns.layout.WarehouseLayout;

public class WarehouseFactory {
    public WarehouseLayout createWarehouseLayout(String layoutName) {
        return new WarehouseLayout(layoutName);
    }
}
