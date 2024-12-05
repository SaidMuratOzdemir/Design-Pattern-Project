package ui;
import javax.swing.*;
import java.awt.*;

public class CategoryManagementUI extends JFrame {
    private JTextField categoryNameField;
    private JButton addCategoryButton, updateCategoryButton, deleteCategoryButton;
    private JList<String> categoryList;
    private DefaultListModel<String> categoryListModel;

    public CategoryManagementUI() {
        setTitle("Category Management");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Başlık
        JLabel headerLabel = new JLabel("Category Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headerLabel, BorderLayout.NORTH);

        // Ana Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Kategori Listesi
        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        JScrollPane scrollPane = new JScrollPane(categoryList);

        // Kategori Giriş Alanı
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        categoryNameField = new JTextField();
        inputPanel.add(new JLabel("Category Name:"));
        inputPanel.add(categoryNameField);

        // Butonlar
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        addCategoryButton = new JButton("Add");
        updateCategoryButton = new JButton("Update");
        deleteCategoryButton = new JButton("Delete");

        buttonPanel.add(addCategoryButton);
        buttonPanel.add(updateCategoryButton);
        buttonPanel.add(deleteCategoryButton);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // Olay Dinleyicileri
        addCategoryButton.addActionListener(e -> addCategory());
        updateCategoryButton.addActionListener(e -> updateCategory());
        deleteCategoryButton.addActionListener(e -> deleteCategory());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addCategory() {
        String categoryName = categoryNameField.getText();
        if (!categoryName.isEmpty()) {
            categoryListModel.addElement(categoryName);
            categoryNameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Category Name cannot be empty!");
        }
    }

    private void updateCategory() {
        int selectedIndex = categoryList.getSelectedIndex();
        String newCategoryName = categoryNameField.getText();
        if (selectedIndex >= 0 && !newCategoryName.isEmpty()) {
            categoryListModel.set(selectedIndex, newCategoryName);
            categoryNameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Select a category and enter a valid name!");
        }
    }

    private void deleteCategory() {
        int selectedIndex = categoryList.getSelectedIndex();
        if (selectedIndex >= 0) {
            categoryListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "No category selected!");
        }
    }
}
