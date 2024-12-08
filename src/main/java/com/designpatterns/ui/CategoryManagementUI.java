package com.designpatterns.ui;

import com.designpatterns.composite.Category;
import com.designpatterns.dao.CategoryDAO;
import com.designpatterns.factory.CategoryFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryManagementUI extends JFrame {
    private JTextField categoryNameField, subCategoryNameField;
    private JButton addButton, deleteButton, updateButton, addSubCategoryButton, removeSubCategoryButton;
    private JList<String> categoryList, subCategoryList;
    private DefaultListModel<String> categoryListModel, subCategoryListModel;

    private static final CategoryDAO categoryDAO = new CategoryDAO();
    private static final CategoryFactory categoryFactory = new CategoryFactory();

    public CategoryManagementUI() {
        setTitle("Category Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("Category Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(headerLabel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        JScrollPane categoryScrollPane = new JScrollPane(categoryList);

        subCategoryListModel = new DefaultListModel<>();
        subCategoryList = new JList<>(subCategoryListModel);
        JScrollPane subCategoryScrollPane = new JScrollPane(subCategoryList);

        mainPanel.add(categoryScrollPane);
        mainPanel.add(subCategoryScrollPane);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        categoryNameField = new JTextField();
        subCategoryNameField = new JTextField();

        inputPanel.add(new JLabel("Category Name:"));
        inputPanel.add(categoryNameField);
        inputPanel.add(new JLabel("Subcategory Name:"));
        inputPanel.add(subCategoryNameField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        addButton = new JButton("Add Category");
        deleteButton = new JButton("Delete Category");
        updateButton = new JButton("Update Category");
        addSubCategoryButton = new JButton("Add Subcategory");
        removeSubCategoryButton = new JButton("Remove Subcategory");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(addSubCategoryButton);
        buttonPanel.add(removeSubCategoryButton);

        add(mainPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> addCategory());
        deleteButton.addActionListener(e -> deleteCategory());
        updateButton.addActionListener(e -> updateCategory());
        addSubCategoryButton.addActionListener(e -> addSubCategory());
        removeSubCategoryButton.addActionListener(e -> removeSubCategory());

        categoryList.addListSelectionListener(e -> loadSubCategories());

        // Load categories on initialization
        loadCategories();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadCategories() {
        categoryListModel.clear();
        List<Category> categories = categoryDAO.getAllCategories();
        for (Category category : categories) {
            if (category.getParent() == null) {
                categoryListModel.addElement(category.getId() + " - " + category.getName());
            }
        }
    }

    private void loadSubCategories() {
        String selectedCategory = categoryList.getSelectedValue();
        if (selectedCategory != null) {
            subCategoryListModel.clear();
            int categoryId = Integer.parseInt(selectedCategory.split(" ")[0]);
            Category parentCategory = categoryDAO.getCategoryById(categoryId);
            if (parentCategory != null) {
                for (Category subCategory : parentCategory.getSubCategories()) {
                    subCategoryListModel.addElement(subCategory.getId() + " - " + subCategory.getName());
                }
            }
        }
    }

    private void addCategory() {
        String categoryName = categoryNameField.getText().trim();
        if (!categoryName.isEmpty()) {
            Category category = categoryFactory.createCategory(categoryName);
            categoryDAO.addCategory(category);
            JOptionPane.showMessageDialog(this, "Category added successfully!");
            categoryNameField.setText("");
            loadCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Category name cannot be empty.");
        }
    }

    private void deleteCategory() {
        String selectedCategory = categoryList.getSelectedValue();
        if (selectedCategory != null) {
            int categoryId = Integer.parseInt(selectedCategory.split(" ")[0]);
            categoryDAO.deleteCategory(categoryId);
            JOptionPane.showMessageDialog(this, "Category deleted successfully!");
            loadCategories();
            subCategoryListModel.clear();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category to delete.");
        }
    }

    private void updateCategory() {
        String selectedCategory = categoryList.getSelectedValue();
        String newCategoryName = categoryNameField.getText().trim();

        if (selectedCategory != null && !newCategoryName.isEmpty()) {
            int categoryId = Integer.parseInt(selectedCategory.split(" ")[0]);
            Category category = categoryDAO.getCategoryById(categoryId);
            if (category != null) {
                category.setName(newCategoryName);
                categoryDAO.updateCategory(category);
                JOptionPane.showMessageDialog(this, "Category updated successfully!");
                categoryNameField.setText("");
                loadCategories();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category and provide a new name.");
        }
    }

    private void addSubCategory() {
        String selectedCategory = categoryList.getSelectedValue();
        String subCategoryName = subCategoryNameField.getText().trim();

        if (selectedCategory != null && !subCategoryName.isEmpty()) {
            int parentCategoryId = Integer.parseInt(selectedCategory.split(" ")[0]);
            Category parentCategory = categoryDAO.getCategoryById(parentCategoryId);
            if (parentCategory != null) {
                Category subCategory = categoryFactory.createCategory(subCategoryName);
                parentCategory.addSubCategory(subCategory);
                categoryDAO.updateCategory(parentCategory);
                JOptionPane.showMessageDialog(this, "Subcategory added successfully!");
                subCategoryNameField.setText("");
                loadSubCategories();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category and provide a subcategory name.");
        }
    }

    private void removeSubCategory() {
        String selectedCategory = categoryList.getSelectedValue();
        String selectedSubCategory = subCategoryList.getSelectedValue();

        if (selectedCategory != null && selectedSubCategory != null) {
            int parentCategoryId = Integer.parseInt(selectedCategory.split(" ")[0]);
            int subCategoryId = Integer.parseInt(selectedSubCategory.split(" ")[0]);

            Category parentCategory = categoryDAO.getCategoryById(parentCategoryId);
            Category subCategory = categoryDAO.getCategoryById(subCategoryId);

            if (parentCategory != null && subCategory != null) {
                parentCategory.removeSubCategory(subCategory);
                subCategory.setParent(null); // Alt kategorinin üst kategori referansını temizle
                categoryDAO.updateCategory(parentCategory);
                categoryDAO.updateCategory(subCategory);

                JOptionPane.showMessageDialog(this, "Subcategory removed successfully!");
                loadSubCategories();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a category and a subcategory to remove.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CategoryManagementUI::new);
    }
}
