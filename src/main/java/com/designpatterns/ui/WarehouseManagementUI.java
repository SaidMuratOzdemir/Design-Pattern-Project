package com.designpatterns.ui;

import com.designpatterns.layout.WarehouseLayout;
import com.designpatterns.dao.WarehouseLayoutDAO;
import com.designpatterns.composite.Product;
import com.designpatterns.dao.ProductDAO;
import com.designpatterns.factory.WarehouseFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WarehouseManagementUI extends JFrame {
    private JTextField warehouseNameField;
    private JButton addButton, deleteButton, updateButton, assignProductButton, removeProductButton;
    private JList<String> warehouseList, productList;
    private DefaultListModel<String> warehouseListModel, productListModel;
    private JComboBox<String> productComboBox;

    private static final WarehouseLayoutDAO warehouseDAO = new WarehouseLayoutDAO();
    private static final ProductDAO productDAO = new ProductDAO();
    private static final WarehouseFactory warehouseFactory = new WarehouseFactory();

    public WarehouseManagementUI() {
        setTitle("Warehouse Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Warehouse Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headerLabel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        warehouseListModel = new DefaultListModel<>();
        warehouseList = new JList<>(warehouseListModel);
        JScrollPane warehouseScrollPane = new JScrollPane(warehouseList);

        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        JScrollPane productScrollPane = new JScrollPane(productList);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        warehouseNameField = new JTextField();
        productComboBox = new JComboBox<>();

        inputPanel.add(new JLabel("Warehouse Name:"));
        inputPanel.add(warehouseNameField);
        inputPanel.add(new JLabel("Assign Product:"));
        inputPanel.add(productComboBox);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        addButton = new JButton("Add Warehouse");
        deleteButton = new JButton("Delete Warehouse");
        updateButton = new JButton("Update Warehouse");
        assignProductButton = new JButton("Assign Product");
        removeProductButton = new JButton("Remove Product");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(assignProductButton);
        buttonPanel.add(removeProductButton);

        mainPanel.add(warehouseScrollPane, BorderLayout.WEST);
        mainPanel.add(productScrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Event Listeners
        addButton.addActionListener(e -> addWarehouse());
        deleteButton.addActionListener(e -> deleteWarehouse());
        updateButton.addActionListener(e -> updateWarehouse());
        assignProductButton.addActionListener(e -> assignProductToWarehouse());
        removeProductButton.addActionListener(e -> removeProductFromWarehouse());

        warehouseList.addListSelectionListener(e -> loadProductsInWarehouse());

        // Initialize Data
        loadWarehouses();
        loadProducts();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadWarehouses() {
        warehouseListModel.clear();
        List<WarehouseLayout> warehouses = warehouseDAO.getAllWarehouseLayouts();
        for (WarehouseLayout warehouse : warehouses) {
            warehouseListModel.addElement(warehouse.getId() + " - " + warehouse.getLayoutName());
        }
    }

    private void loadProducts() {
        productComboBox.removeAllItems();
        List<Product> products = productDAO.getAllProducts();
        for (Product product : products) {
            productComboBox.addItem(product.getProductId() + " - " + product.getName());
        }
    }

    private void loadProductsInWarehouse() {
        String selectedWarehouse = warehouseList.getSelectedValue();
        if (selectedWarehouse != null) {
            productListModel.clear();
            int warehouseId = Integer.parseInt(selectedWarehouse.split(" ")[0]);
            WarehouseLayout warehouse = warehouseDAO.getWarehouseLayoutById(warehouseId);
            if (warehouse != null) {
                for (Product product : warehouse.getProducts()) {
                    productListModel.addElement(product.getProductId() + " - " + product.getName() + " (Stock: " + product.getStock() + ")");
                }
            }
        }
    }

    private void addWarehouse() {
        String warehouseName = warehouseNameField.getText().trim();
        if (!warehouseName.isEmpty()) {
            WarehouseLayout warehouse = warehouseFactory.createWarehouseLayout(warehouseName);
            warehouseDAO.addWarehouseLayout(warehouse);
            JOptionPane.showMessageDialog(this, "Warehouse added successfully!");
            warehouseNameField.setText("");
            loadWarehouses();
        } else {
            JOptionPane.showMessageDialog(this, "Warehouse name cannot be empty.");
        }
    }

    private void deleteWarehouse() {
        String selectedWarehouse = warehouseList.getSelectedValue();
        if (selectedWarehouse != null) {
            int warehouseId = Integer.parseInt(selectedWarehouse.split(" ")[0]);
            warehouseDAO.deleteWarehouseLayout(warehouseId);
            JOptionPane.showMessageDialog(this, "Warehouse deleted successfully!");
            loadWarehouses();
            productListModel.clear();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a warehouse to delete.");
        }
    }

    private void updateWarehouse() {
        String selectedWarehouse = warehouseList.getSelectedValue();
        String newWarehouseName = warehouseNameField.getText().trim();

        if (selectedWarehouse != null && !newWarehouseName.isEmpty()) {
            int warehouseId = Integer.parseInt(selectedWarehouse.split(" ")[0]);
            WarehouseLayout warehouse = warehouseDAO.getWarehouseLayoutById(warehouseId);
            if (warehouse != null) {
                warehouse.setLayoutName(newWarehouseName);
                warehouseDAO.updateWarehouseLayout(warehouse);
                JOptionPane.showMessageDialog(this, "Warehouse updated successfully!");
                warehouseNameField.setText("");
                loadWarehouses();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a warehouse and provide a new name.");
        }
    }

    private void assignProductToWarehouse() {
        String selectedWarehouse = warehouseList.getSelectedValue();
        String selectedProduct = (String) productComboBox.getSelectedItem();

        if (selectedWarehouse != null && selectedProduct != null) {
            int warehouseId = Integer.parseInt(selectedWarehouse.split(" ")[0]);
            WarehouseLayout warehouse = warehouseDAO.getWarehouseLayoutById(warehouseId);
            String productId = selectedProduct.split(" ")[0];
            Product product = productDAO.getProductByProductId(productId);

            if (warehouse != null && product != null) {
                warehouse.addProduct(product);
                warehouseDAO.updateWarehouseLayout(warehouse);
                JOptionPane.showMessageDialog(this, "Product assigned to warehouse successfully!");
                loadProductsInWarehouse();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a warehouse and a product.");
        }
    }

    private void removeProductFromWarehouse() {
        String selectedWarehouse = warehouseList.getSelectedValue();
        String selectedProduct = productList.getSelectedValue();

        if (selectedWarehouse != null && selectedProduct != null) {
            int warehouseId = Integer.parseInt(selectedWarehouse.split(" ")[0]);
            WarehouseLayout warehouse = warehouseDAO.getWarehouseLayoutById(warehouseId);
            String productId = selectedProduct.split(" ")[0];
            Product product = productDAO.getProductByProductId(productId);

            if (warehouse != null && product != null) {
                warehouse.removeProduct(product);
                warehouseDAO.updateWarehouseLayout(warehouse);
                JOptionPane.showMessageDialog(this, "Product removed from warehouse successfully!");
                loadProductsInWarehouse();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a warehouse and a product.");
        }
    }
}
