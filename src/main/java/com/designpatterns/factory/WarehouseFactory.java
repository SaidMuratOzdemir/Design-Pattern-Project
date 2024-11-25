package com.designpatterns.factory;


import com.designpatterns.layout.WarehouseLayout;
import com.designpatterns.dao.WarehouseLayoutDAO;

public class WarehouseFactory {
    private WarehouseLayoutDAO warehouseLayoutDAO;

    public WarehouseFactory() {
        this.warehouseLayoutDAO = new WarehouseLayoutDAO();
    }

    // Depo düzeni oluşturma metodu ve veritabanına ekleme
    public WarehouseLayout createWarehouseLayout(String layoutName) {
        WarehouseLayout layout = new WarehouseLayout(layoutName);
        warehouseLayoutDAO.addWarehouseLayout(layout);
        return layout;
    }
}

