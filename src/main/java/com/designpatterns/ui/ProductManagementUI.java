package com.designpatterns.ui;

import com.designpatterns.composite.Category;
import com.designpatterns.composite.Product;
import com.designpatterns.dao.CategoryDAO;
import com.designpatterns.dao.ProductDAO;
import com.designpatterns.factory.ProductFactory;
import com.designpatterns.observer.StockManager;
import com.designpatterns.layout.WarehouseLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductManagementUI extends JFrame {
    private JTextField productIdField, productNameField, productStockField, stockUpdateField, searchField;
    private JButton addButton, deleteButton, assignCategoryButton, updateStockButton, sortButton, lowStockButton, showWarehouseButton, listByCategoryButton, searchButton;
    private JList<String> productList, categoryList;
    private DefaultListModel<String> productListModel, categoryListModel;

    private static final ProductDAO productDAO = new ProductDAO();
    private static final CategoryDAO categoryDAO = new CategoryDAO();
    private static final ProductFactory productFactory = new ProductFactory();
    private static final StockManager stockManager = new StockManager();

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
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        productIdField = new JTextField();
        productNameField = new JTextField();
        productStockField = new JTextField();
        searchField = new JTextField();

        inputPanel.add(new JLabel("Product ID:"));
        inputPanel.add(productIdField);
        inputPanel.add(new JLabel("Product Name:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Stock:"));
        inputPanel.add(productStockField);
        inputPanel.add(new JLabel("Search by Name:"));
        inputPanel.add(searchField);

        // Stock Update Panel
        JPanel stockUpdatePanel = new JPanel(new GridLayout(1, 2, 10, 10));
        stockUpdateField = new JTextField();
        updateStockButton = new JButton("Update Stock");

        stockUpdatePanel.add(new JLabel("Stock Change (+ or -):"));
        stockUpdatePanel.add(stockUpdateField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        addButton = new JButton("Add Product");
        deleteButton = new JButton("Delete Product");
        assignCategoryButton = new JButton("Assign Category");
        sortButton = new JButton("Sort by Stock");
        lowStockButton = new JButton("Low Stock Products");
        showWarehouseButton = new JButton("Show Warehouse");
        listByCategoryButton = new JButton("List by Category");
        searchButton = new JButton("Search");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(assignCategoryButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(lowStockButton);
        buttonPanel.add(showWarehouseButton);
        buttonPanel.add(listByCategoryButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(stockUpdatePanel);
        buttonPanel.add(updateStockButton);

        add(mainPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> addProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        assignCategoryButton.addActionListener(e -> assignCategory());
        updateStockButton.addActionListener(e -> updateStock());
        sortButton.addActionListener(e -> sortByStock());
        lowStockButton.addActionListener(e -> showLowStockProducts());
        showWarehouseButton.addActionListener(e -> showProductWarehouse());
        listByCategoryButton.addActionListener(e -> listByCategory());
        searchButton.addActionListener(e -> searchProduct());

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

    private void searchProduct() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            List<Product> foundProducts = productDAO.searchProductsByName(searchTerm);
            productListModel.clear();
            if (!foundProducts.isEmpty()) {
                for (Product product : foundProducts) {
                    productListModel.addElement(product.getId() + " - " + product.getName() + " | Stock: " + product.getStock());
                }
            } else {
                JOptionPane.showMessageDialog(this, "No products found matching the search term.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a search term.");
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

    private void updateStock() {
        String selectedProduct = productList.getSelectedValue();
        String stockChangeString = stockUpdateField.getText().trim();

        if (selectedProduct != null && !stockChangeString.isEmpty()) {
            try {
                int stockChange = Integer.parseInt(stockChangeString);
                int productId = Integer.parseInt(selectedProduct.split(" ")[0]);
                Product product = productDAO.getProductById(productId);

                if (product != null) {
                    stockManager.updateStock(product, stockChange); // Observer'dan stok güncellemesi yapılıyor
                    JOptionPane.showMessageDialog(this, "Stock updated successfully!");
                    loadProducts();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Stock change must be a valid number.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a product and provide a valid stock change.");
        }
    }

    // Ürünleri stok seviyesine göre sıralama (artış/azalış)
    private void sortByStock() {
        String order = JOptionPane.showInputDialog(this, "Enter 'asc' for ascending or 'desc' for descending");
        if (order != null && (order.equals("asc") || order.equals("desc"))) {
            List<Product> sortedProducts = productDAO.getProductsSortedByStock(order.equals("asc"));
            productListModel.clear();
            for (Product product : sortedProducts) {
                productListModel.addElement(product.getId() + " - " + product.getName() + " | Stock: " + product.getStock());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter 'asc' or 'desc'.");
        }
    }

    // Düşük stoklu ürünleri raporlama
    private void showLowStockProducts() {
        String thresholdString = JOptionPane.showInputDialog(this, "Enter the stock threshold:");
        if (thresholdString != null) {
            try {
                int threshold = Integer.parseInt(thresholdString);
                List<Product> lowStockProducts = productDAO.getLowStockProducts(threshold);
                if (lowStockProducts.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No low stock products found.");
                } else {
                    DefaultListModel<String> lowStockModel = new DefaultListModel<>();
                    for (Product product : lowStockProducts) {
                        lowStockModel.addElement(product.getId() + " - " + product.getName() + " | Stock: " + product.getStock());
                    }
                    JOptionPane.showMessageDialog(this, new JList<>(lowStockModel), "Low Stock Products", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for threshold.");
            }
        }
    }

    // Ürünün ait olduğu depo düzenini gösterme
    private void showProductWarehouse() {
        String selectedProduct = productList.getSelectedValue();
        if (selectedProduct != null) {
            int productId = Integer.parseInt(selectedProduct.split(" ")[0]);
            Product product = productDAO.getProductById(productId);
            if (product != null && product.getWarehouseLayout() != null) {
                WarehouseLayout warehouse = product.getWarehouseLayout();
                JOptionPane.showMessageDialog(this, "Product is in warehouse: " + warehouse.getLayoutName());
            } else {
                JOptionPane.showMessageDialog(this, "No warehouse assigned to this product.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a product to view its warehouse.");
        }
    }

    // Belirli bir kategorideki ürünleri listeleme
    private void listByCategory() {
        String selectedCategory = categoryList.getSelectedValue();
        if (selectedCategory != null) {
            int categoryId = Integer.parseInt(selectedCategory.split(" ")[0]);
            List<Product> productsByCategory = productDAO.getProductsByCategoryId(categoryId);
            if (productsByCategory != null && !productsByCategory.isEmpty()) {
                DefaultListModel<String> categoryProductModel = new DefaultListModel<>();
                for (Product product : productsByCategory) {
                    categoryProductModel.addElement(product.getId() + " - " + product.getName() + " | Stock: " + product.getStock());
                }
                JOptionPane.showMessageDialog(this, new JList<>(categoryProductModel), "Products in Category", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No products found in this category.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a category to view its products.");
        }
    }

    private void clearFields() {
        productIdField.setText("");
        productNameField.setText("");
        productStockField.setText("");
        stockUpdateField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductManagementUI::new);
    }
}
