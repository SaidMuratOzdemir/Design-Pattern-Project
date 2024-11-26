package com.designpatterns.composite;


import com.designpatterns.layout.WarehouseLayout;
import com.designpatterns.observer.Subject;
import jakarta.persistence.*;


@Entity
@Table(name = "product")
public class Product implements InventoryComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "warehouse_layout_id")
    private WarehouseLayout warehouseLayout;

    public Product() {
        this.subject = new Subject();
    }

    public Product(String productId, String name, int stock) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.subject = new Subject();
    }

    public int getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getStock() {
        return stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public WarehouseLayout getWarehouseLayout() {
        return warehouseLayout;
    }

    public void setWarehouseLayout(WarehouseLayout warehouseLayout) {
        this.warehouseLayout = warehouseLayout;
    }

    public void updateStock(int quantity) {
        this.stock += quantity;
    }

    public void display(String indent) {
        System.out.println(indent + "Product: " + name + " | Stock: " + stock);
    }
}
