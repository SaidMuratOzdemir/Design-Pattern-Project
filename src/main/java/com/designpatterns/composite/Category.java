package com.designpatterns.composite;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Category sınıfı, Composite pattern'ini kullanarak kategorileri hiyerarşik olarak yönetir
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; // Üst kategori

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Category> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    // Boş constructor (JPA için gereklidir)
    public Category() {}

    // İsme göre constructor
    public Category(String name) {
        this.name = name;
    }

    // Getter ve Setter metodları
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public List<Product> getProducts() {
        return products;
    }

    // Alt kategori ekleme
    public void addSubCategory(Category subCategory) {
        subCategories.add(subCategory);
        subCategory.setParent(this);
    }

    // Alt kategori çıkarma
    public void removeSubCategory(Category subCategory) {
        subCategories.remove(subCategory);
        subCategory.setParent(null);
    }

    // Ürün ekleme
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

    // Ürün çıkarma
    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }

    // Kategori ve ilişkili alt kategorileri ve ürünleri göstermek için display metodu
    public void display(String indent) {
        System.out.println(indent + "Category: " + name);
        for (Product product : products) {
            product.display(indent + "  ");
        }
        for (Category subCategory : subCategories) {
            subCategory.display(indent + "  ");
        }
    }
}
