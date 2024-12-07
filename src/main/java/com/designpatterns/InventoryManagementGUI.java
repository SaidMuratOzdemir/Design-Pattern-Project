package com.designpatterns;

import com.designpatterns.alerts.AlertSystem;
import com.designpatterns.observer.StockManager;
import com.designpatterns.ui.CategoryManagementUI;
import com.designpatterns.ui.ProductManagementUI;
import com.designpatterns.ui.WarehouseManagementUI;

import javax.swing.*;
import java.awt.*;

public class InventoryManagementGUI extends JFrame {
    private JButton categoryButton, productButton, warehouseButton, exitButton;
    private static final StockManager stockManager = new StockManager();
    private static final AlertSystem alertSystem = new AlertSystem(10); // Min stok seviyesi 10

    public InventoryManagementGUI() {
        // AlertSystem Observer olarak ekleniyor
        stockManager.addObserver(alertSystem);

        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        // Menu Panel
        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        categoryButton = new JButton("Category Management");
        productButton = new JButton("Product Management");
        warehouseButton = new JButton("Warehouse Management");
        exitButton = new JButton("Exit");

        menuPanel.add(categoryButton);
        menuPanel.add(productButton);
        menuPanel.add(warehouseButton);
        menuPanel.add(exitButton);

        add(menuPanel, BorderLayout.CENTER);

        // Event Listeners
        categoryButton.addActionListener(e -> new CategoryManagementUI());
        productButton.addActionListener(e -> new ProductManagementUI());
        warehouseButton.addActionListener(e -> new WarehouseManagementUI());
        exitButton.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InventoryManagementGUI();
            System.out.println("Alert System initialized and monitoring stock levels.");
        });
    }
}
