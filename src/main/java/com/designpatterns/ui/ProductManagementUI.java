package com.designpatterns.ui;

import com.designpatterns.composite.Category;
import com.designpatterns.composite.Product;
import com.designpatterns.dao.CategoryDAO;
import com.designpatterns.dao.ProductDAO;
import com.designpatterns.factory.ProductFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductManagementUI extends JFrame {
    private JTextField productIdField, productNameField, productStockField;
    private JButton addButton, updateButton, deleteButton, assignCategoryButton;
    private JList<String> productList, categoryList;
    private DefaultListModel<String> productListModel, categoryListModel;

    private static final ProductDAO productDAO = new ProductDAO();
    private static final CategoryDAO categoryDAO = new CategoryDAO();
    private static final ProductFactory productFactory = new ProductFactory();

    public ProductManagementUI() {
        setTitle("Product Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Product Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headerLabel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        JScrollPane productScrollPane = new JScrollPane(productList);

        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        JScrollPane categoryScrollPane = new JScrollPane(categoryList);

        mainPanel.add(productScrollPane);
        mainPanel.add(categoryScrollPane);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        productIdField = new JTextField();
        productNameField = new JTextField();
        productStockField = new JTextField();

        inputPanel.add(new JLabel("Product ID:"));
        inputPanel.add(productIdField);
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Stock:"));
        inputPanel.add(productStockField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        addButton = new JButton("Add Product");
        updateButton = new JButton("Update Product");
        deleteButton = new JButton("Delete Product");
        assignCategoryButton = new JButton("Assign Category");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(assignCategoryButton);

        add(mainPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        assignCategoryButton.addActionListener(e -> assignCategory());

        productList.addListSelectionListener(e -> loadCategories());
        loadProducts();
        loadCategories();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadProducts() {
        productListModel.clear();
        List<Product> products = productDAO.getAllProducts();
        for (Product product : products) {
            productListModel.addElement(product.getId() + " - " + product.getName() + " | Stock: " + product.getStock());
        }
    }

    private void loadCategories() {
        categoryListModel.clear();
        List<Category> categories = categoryDAO.getAllCategories();
        for (Category category : categories) {
            categoryListModel.addElement(category.getId() + " - " + category.getName());
        }
    }

    private void addProduct() {
        String productId = productIdField.getText().trim();
        String productName = productNameField.getText().trim();
        String stockString = productStockField.getText().trim();

        if (!productId.isEmpty() && !productName.isEmpty() && !stockString.isEmpty()) {
            try {
                int stock = Integer.parseInt(stockString);
                Product product = productFactory.createProduct(productId, productName, stock);
                productDAO.addProduct(product);
                JOptionPane.showMessageDialog(this, "Product added successfully!");
                clearFields();
                loadProducts();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stock must be a valid number.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "All fields are required.");
        }
    }

    private void updateProduct() {
        String selectedProduct = productList.getSelectedValue();
        String newProductName = productNameField.getText().trim();
        String stockString = productStockField.getText().trim();

        if (selectedProduct != null && !newProductName.isEmpty() && !stockString.isEmpty()) {
            try {
                int stock = Integer.parseInt(stockString);
                int productId = Integer.parseInt(selectedProduct.split(" ")[0]);
                Product product = productDAO.getProductById(productId);

                if (product != null) {
                    product.setName(newProductName);
                    product.updateStock(stock);
                    productDAO.updateProduct(product);
                    JOptionPane.showMessageDialog(this, "Product updated successfully!");
                    clearFields();
                    loadProducts();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stock must be a valid number.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a product and provide updated information.");
        }
    }

    private void deleteProduct() {
        String selectedProduct = productList.getSelectedValue();

        if (selectedProduct != null) {
            int productId = Integer.parseInt(selectedProduct.split(" ")[0]);
            productDAO.deleteProduct(productId);
            JOptionPane.showMessageDialog(this, "Product deleted successfully!");
            loadProducts();
        } else {
            JOptionPane.showMessageDialog(this, "Select a product to delete.");
        }
    }

    private void assignCategory() {
        String selectedProduct = productList.getSelectedValue();
        String selectedCategory = categoryList.getSelectedValue();

        if (selectedProduct != null && selectedCategory != null) {
            int productId = Integer.parseInt(selectedProduct.split(" ")[0]);
            int categoryId = Integer.parseInt(selectedCategory.split(" ")[0]);

            Product product = productDAO.getProductById(productId);
            Category category = categoryDAO.getCategoryById(categoryId);

            if (product != null && category != null) {
                product.setCategory(category);
                productDAO.updateProduct(product);
                JOptionPane.showMessageDialog(this, "Category assigned successfully!");
                loadProducts();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a product and a category.");
        }
    }

    private void clearFields() {
        productIdField.setText("");
        productNameField.setText("");
        productStockField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductManagementUI::new);
    }
}
