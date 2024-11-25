package com.designpatterns.composite;


import com.designpatterns.observer.Observer;
import com.designpatterns.observer.Subject;
import jakarta.persistence.*;


@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Veritabanı ID'si

    @Column(name = "product_id", nullable = false, unique = true)
    private String productId; // Ürün ID'si

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; // Kategori

    @Transient
    private Subject subject; // Observer Pattern için Subject

    public Product() {
        this.subject = new Subject();
    }

    public Product(String productId, String name, int stock) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.subject = new Subject();
    }

    // Getter ve Setter metodları

    public int getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public void setStock(int stock) {
        this.stock = stock;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Observer Pattern metotları
    public void addObserver(Observer observer) {
        subject.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        subject.removeObserver(observer);
    }

    // Stok güncelleme metodu
    public void updateStock(int quantity) {
        this.stock += quantity;
        // Observer'lara bildirim gönder
        subject.notifyObservers(this);
    }

    // Ürün bilgilerini görüntüleme metodu
    public void display(String indent) {
        System.out.println(indent + "Product: " + name + " | Stock: " + stock);
    }
}
