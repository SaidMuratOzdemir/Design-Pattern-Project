package com.designpatterns.composite;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category implements InventoryComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Category> subCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    // JPA İÇİN GEREKLİ ELLEMEYİN
    public Category() {}

    public Category(String name) {
        this.name = name;
    }

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

    public void addSubCategory(Category subCategory) {
        if (subCategory == null) {
            throw new IllegalArgumentException("Alt kategori null olamaz.");
        }
        subCategories.add(subCategory);
        subCategory.setParent(this);
    }

    public void removeSubCategory(Category subCategory) {
        if (subCategory != null && subCategories.contains(subCategory)) {
            subCategories.remove(subCategory);
            subCategory.setParent(null);
        }
    }

    public void display(String indent) {
        System.out.println(indent + "- " + name + " (ID: " + id + ")");
        for (Category sub : subCategories) {
            sub.display(indent + "  ");
        }
    }
}
