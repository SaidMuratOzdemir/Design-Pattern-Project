package ui;
import javax.swing.*;
import java.awt.*;

public class StockManagementUI extends JFrame {
    private JTextField productNameField, stockQuantityField;
    private JButton addStockButton, checkStockButton;

    public StockManagementUI() {
        setTitle("Stock Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Başlık
        JLabel headerLabel = new JLabel("Stock Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headerLabel, BorderLayout.NORTH);

        // Ana Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

        mainPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        mainPanel.add(productNameField);

        mainPanel.add(new JLabel("Stock Quantity:"));
        stockQuantityField = new JTextField();
        mainPanel.add(stockQuantityField);

        addStockButton = new JButton("Add Stock");
        checkStockButton = new JButton("Check Stock");

        mainPanel.add(addStockButton);
        mainPanel.add(checkStockButton);

        add(mainPanel, BorderLayout.CENTER);

        // Olay Dinleyicileri
        addStockButton.addActionListener(e -> addStock());
        checkStockButton.addActionListener(e -> checkStock());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addStock() {
        String productName = productNameField.getText();
        String quantity = stockQuantityField.getText();

        if (!productName.isEmpty() && !quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stock Updated: " + productName + " - " + quantity + " units added.");
        } else {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        }
    }

    private void checkStock() {
        JOptionPane.showMessageDialog(this, "Check Stock functionality coming soon!");
    }
}
