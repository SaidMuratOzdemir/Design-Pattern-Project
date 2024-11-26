package com.designpatterns.layout;

import com.designpatterns.composite.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "warehouse_layout")
public class WarehouseLayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String layoutName;

    @OneToMany(mappedBy = "warehouseLayout", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    public WarehouseLayout() {}

    public WarehouseLayout(String layoutName) {
        this.layoutName = layoutName;
    }

    public int getId() {
        return id;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
        product.setWarehouseLayout(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setWarehouseLayout(null);
    }

    public void displayLayout() {
        System.out.println("Depo Düzeni: " + layoutName);
        System.out.println("Bu depodaki ürünler:");
        for (Product product : products) {
            System.out.println("  - Ürün: " + product.getName() + " | Stok: " + product.getStock());
        }
    }
}
