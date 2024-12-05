package ui;
import javax.swing.*;
import java.awt.*;

public class ProductManagementUI extends JFrame {
    private JTextField productNameField, productPriceField;
    private JComboBox<String> categoryComboBox;
    private JButton addButton, updateButton, deleteButton;

    public ProductManagementUI() {
        setTitle("Product Management");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Başlık
        JLabel headerLabel = new JLabel("Product Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headerLabel, BorderLayout.NORTH);

        // Ana Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

        mainPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        mainPanel.add(productNameField);

        mainPanel.add(new JLabel("Price:"));
        productPriceField = new JTextField();
        mainPanel.add(productPriceField);

        mainPanel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>(new String[]{"Category 1", "Category 2", "Category 3"});
        mainPanel.add(categoryComboBox);

        addButton = new JButton("Add Product");
        updateButton = new JButton("Update Product");
        deleteButton = new JButton("Delete Product");

        mainPanel.add(addButton);
        mainPanel.add(updateButton);
        mainPanel.add(deleteButton);

        add(mainPanel, BorderLayout.CENTER);

        // Olay Dinleyicileri
        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addProduct() {
        String productName = productNameField.getText();
        String price = productPriceField.getText();
        String category = (String) categoryComboBox.getSelectedItem();

        if (!productName.isEmpty() && !price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product Added: " + productName + ", Price: " + price + ", Category: " + category);
        } else {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
        }
    }

    private void updateProduct() {
        JOptionPane.showMessageDialog(this, "Update Product functionality coming soon!");
    }

    private void deleteProduct() {
        JOptionPane.showMessageDialog(this, "Delete Product functionality coming soon!");
    }
}
