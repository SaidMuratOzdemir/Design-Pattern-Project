package com.designpatterns.layout;


import jakarta.persistence.*;

@Entity
@Table(name = "warehouse_layout")
public class WarehouseLayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String layoutName;

    public WarehouseLayout() {}

    public WarehouseLayout(String layoutName) {
        this.layoutName = layoutName;
    }

    // Getter ve Setter metodları

    public int getId() {
        return id;
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public void displayLayout() {
        // Depo düzenini görüntüleme mantığı
        System.out.println("Depo Düzeni: " + layoutName);
    }
}
