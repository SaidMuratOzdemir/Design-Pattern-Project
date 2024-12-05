package ui;
import javax.swing.*;
import java.awt.*;

public class InventoryManagementUI extends JFrame {
    private JPanel mainPanel;
    private JButton categoryButton, productButton, stockButton, alertsButton;

    public InventoryManagementUI() {
        setTitle("Inventory Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Başlık Paneli
        JLabel headerLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        // Ana Menü Paneli
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2, 10, 10));

        categoryButton = new JButton("Manage Categories");
        productButton = new JButton("Manage Products");
        stockButton = new JButton("Check Stock");
        alertsButton = new JButton("View Alerts");

        mainPanel.add(categoryButton);
        mainPanel.add(productButton);
        mainPanel.add(stockButton);
        mainPanel.add(alertsButton);

        add(mainPanel, BorderLayout.CENTER);

        // Olay Dinleyicileri
        categoryButton.addActionListener(e -> new CategoryManagementUI());
        productButton.addActionListener(e -> new ProductManagementUI());
        stockButton.addActionListener(e -> new StockManagementUI());
        alertsButton.addActionListener(e -> new AlertsUI());

        // Görünür Yap
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManagementUI::new);
    }
}
