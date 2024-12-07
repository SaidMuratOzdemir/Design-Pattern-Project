package com.designpatterns;

import com.designpatterns.alerts.AlertSystem;
import com.designpatterns.composite.Category;
import com.designpatterns.composite.Product;
import com.designpatterns.dao.CategoryDAO;
import com.designpatterns.dao.ProductDAO;
import com.designpatterns.dao.WarehouseLayoutDAO;
import com.designpatterns.factory.CategoryFactory;
import com.designpatterns.factory.ProductFactory;
import com.designpatterns.factory.WarehouseFactory;
import com.designpatterns.layout.WarehouseLayout;
import com.designpatterns.observer.StockManager;
import com.designpatterns.database.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class InventoryManagementSystem {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryDAO categoryDAO = new CategoryDAO();
    private static final ProductDAO productDAO = new ProductDAO();
    private static final WarehouseLayoutDAO warehouseLayoutDAO = new WarehouseLayoutDAO();

    private static final CategoryFactory categoryFactory = new CategoryFactory();
    private static final ProductFactory productFactory = new ProductFactory();
    private static final WarehouseFactory warehouseFactory = new WarehouseFactory();

    private static final StockManager stockManager = new StockManager();
    private static final AlertSystem alertSystem = new AlertSystem(10); // Minimum stok seviyesi 10

    public static void main(String[] args) {
        stockManager.addObserver(alertSystem);

        runMenu();

        scanner.close();
        HibernateUtil.shutdown();
        System.exit(0);
    }

    private static void printMainMenu() {
        System.out.println("\n=== Envanter Yönetim Sistemi ===");
        System.out.println("1. Kategori İşlemleri");
        System.out.println("2. Ürün İşlemleri");
        System.out.println("3. Depo Düzeni İşlemleri");
        System.out.println("0. Çıkış");
    }

    private static void handleCategoryMenu() {
        System.out.println("\n--- Kategori İşlemleri ---");
        System.out.println("1. Kategori Ekle");
        System.out.println("2. Alt Kategori Ekle");
        System.out.println("3. Tüm Kategorileri Listele");
        System.out.println("4. Alt Kategorileri Listele");
        System.out.println("5. Parent Kategori Değiştir");
        System.out.println("6. Kategori Sil");
        System.out.println("7. Alt Kategori Kaldır");
        System.out.println("0. Ana Menüye Dön");

        int choice = getIntegerInput("Kategori menüsünden seçim yapın (0-6): ");
        switch (choice) {
            case 1:
                addCategory();
                break;
            case 2:
                addSubCategory();
                break;
            case 3:
                listCategories();
                break;
            case 4:
                listSubCategories();
                break;
            case 5:
                changeParentCategory();
                break;
            case 6:
                deleteCategory();
                break;
            case 7:
                removeSubCategory();
                break;
            case 0:
                System.out.println("Ana menüye dönülüyor...");
                break;
            default:
                System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
        }
    }

    private static void handleProductMenu() {
        System.out.println("\n--- Ürün İşlemleri ---");
        System.out.println("1. Ürün Ekle");
        System.out.println("2. Tüm Ürünleri Listele");
        System.out.println("3. Belirli Bir Kategorideki Ürünleri Listele");
        System.out.println("4. Ürün Ara");
        System.out.println("5. Stok Güncelle");
        System.out.println("6. Ürün Sil");
        System.out.println("7. Stok Seviyesine Göre Ürünleri Sırala");
        System.out.println("8. Düşük Stoklu Ürünleri Raporla");
        System.out.println("9. Ürünün Ait Olduğu Depo Düzenini Göster");
        System.out.println("0. Ana Menüye Dön");

        int choice = getIntegerInput("Ürün menüsünden seçim yapın (0-8): ");
        switch (choice) {
            case 1:
                addProduct();
                break;
            case 2:
                listProducts();
                break;
            case 3:
                listProductsByCategory();
                break;
            case 4:
                searchProduct();
                break;
            case 5:
                updateStock();
                break;
            case 6:
                deleteProduct();
                break;
            case 7:
                sortProductsByStock();
                break;
            case 8:
                reportLowStockProducts();
                break;
            case 9:
                getWarehouseLayout();
            case 0:
                System.out.println("Ana menüye dönülüyor...");
                break;
            default:
                System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
        }
    }


    private static void handleWarehouseMenu() {
        System.out.println("\n--- Depo Düzeni İşlemleri ---");
        System.out.println("1. Depo Düzeni Ekle");
        System.out.println("2. Tüm Depo Düzenlerini Listele");
        System.out.println("3. Ürünleri Depoya Ata");
        System.out.println("4. Depodaki Ürünleri Listele");
        System.out.println("5. Depoda Düzeni İsmini Değiştir");
        System.out.println("0. Ana Menüye Dön");

        int choice = getIntegerInput("Depo düzeni menüsünden seçim yapın (0-4): ");
        switch (choice) {
            case 1:
                addWarehouseLayout();
                break;
            case 2:
                listWarehouseLayouts();
                break;
            case 3:
                assignProductToWarehouse();
                break;
            case 4:
                listProductsInWarehouse();
                break;
            case 5:
                changeWarehouseLayoutName();
                break;
            case 0:
                System.out.println("Ana menüye dönülüyor...");
                break;
            default:
                System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
        }
    }


    private static void runMenu() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int mainChoice = getIntegerInput("Ana menüden seçim yapın (0-3): ");
            switch (mainChoice) {
                case 1:
                    handleCategoryMenu();
                    break;
                case 2:
                    handleProductMenu();
                    break;
                case 3:
                    handleWarehouseMenu();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Çıkış yapılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
            }
        }
    }


    private static void addCategory() {
        System.out.println("\n--- 2 Ekle ---");
        String name = getStringInput("Kategori adı: ");

        System.out.print("Üst kategori seçmek istiyor musunuz? (evet/hayır): ");
        String hasParent = scanner.nextLine().trim().toLowerCase();
        Category parent = null;
        if (hasParent.equals("evet") || hasParent.equals("e")) {
            parent = selectCategory();
            if (parent == null) {
                System.out.println("Üst kategori seçilemedi. Kategori ana kategori olarak ekleniyor.");
            }
        }

        Category category = categoryFactory.createCategory(name);
        category.setParent(parent);

        categoryDAO.addCategory(category);

        System.out.println("Kategori eklendi: " + category.getName());
    }

    private static void listCategories() {
        System.out.println("\n--- Tüm Kategoriler ---");
        List<Category> categories = categoryDAO.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            System.out.println("Hiç kategori bulunmamaktadır.");
            return;
        }

        for (Category category : categories) {
            if (category.getParent() == null) {
                displayCategoryHierarchy(category, "");
            }
        }
    }

    private static void displayCategoryHierarchy(Category category, String indent) {
        System.out.println(indent + "- " + category.getName());
        for (Category subCategory : category.getSubCategories()) {
            displayCategoryHierarchy(subCategory, indent + "  ");
        }
    }


    private static void addProduct() {
        System.out.println("\n--- Ürün Ekle ---");
        String productId = getStringInput("Ürün ID'si: ");
        String name = getStringInput("Ürün adı: ");
        int stock = getIntegerInput("Stok miktarı: ");

        Category category = selectCategory();
        if (category == null) {
            System.out.println("Kategori seçilemedi. Ürün eklenemiyor.");
            return;
        }

        // create product properly with factory
        Product product = productFactory.createProduct(productId, name, stock);
        product.setCategory(category);

        System.out.println("Ürün eklendi: " + product.getName());
    }

    private static void listProducts() {
        System.out.println("\n--- Tüm Ürünler ---");
        List<Product> products = productDAO.getAllProducts();
        if (products == null || products.isEmpty()) {
            System.out.println("Hiç ürün bulunmamaktadır.");
            return;
        }

        for (Product product : products) {
            System.out.println("Ürün ID: " + product.getProductId() + " | Adı: " + product.getName() + " | Stok: " + product.getStock() + " | Kategori: " + (product.getCategory() != null ? product.getCategory().getName() : "Belirtilmemiş"));
        }
    }

    private static void updateStock() {
        System.out.println("\n--- Stok Güncelle ---");
        Product product = selectProduct();
        if (product == null) {
            System.out.println("Ürün seçilemedi. Stok güncellenemiyor.");
            return;
        }

        int quantity = getIntegerInput("Eklenecek/Çıkarılacak stok miktarı (negatif için çıkartma): ");
        stockManager.updateStock(product, quantity);
        System.out.println("Stok güncellendi. Yeni stok: " + product.getStock());
    }

    private static void deleteCategory() {
        System.out.println("\n--- Kategori Sil ---");
        Category category = selectCategory();
        if (category == null) {
            System.out.println("Kategori seçilemedi. Silme işlemi iptal edildi.");
            return;
        }

        categoryDAO.deleteCategory(category.getId());
        System.out.println("Kategori silindi: " + category.getName());
    }

    private static void deleteProduct() {
        System.out.println("\n--- Ürün Sil ---");
        Product product = selectProduct();
        if (product == null) {
            System.out.println("Ürün seçilemedi. Silme işlemi iptal edildi.");
            return;
        }

        productDAO.deleteProduct(product.getId());
        System.out.println("Ürün silindi: " + product.getName());
    }

    private static void listProductsByCategory() {
        System.out.println("\n--- Kategorideki Ürünleri Listele ---");
        Category category = selectCategory();
        if (category == null) {
            System.out.println("Kategori seçilemedi.");
            return;
        }

        System.out.println("Kategori: " + category.getName() + " Ürünler:");
        for (Product product : category.getProducts()) {
            System.out.println("  - Ürün ID: " + product.getProductId() + " | Adı: " + product.getName() + " | Stok: " + product.getStock());
        }
    }


    private static void searchProduct() {
        System.out.println("\n--- Ürün Ara ---");
        String searchTerm = getStringInput("Aramak istediğiniz ürün adı veya ID'si: ");

        Product productById = productDAO.getProductByProductId(searchTerm);
        if (productById != null) {
            System.out.println("Ürün bulundu:");
            System.out.println("Ürün ID: " + productById.getProductId() + " | Adı: " + productById.getName() + " | Stok: " + productById.getStock() + " | Kategori: " + (productById.getCategory() != null ? productById.getCategory().getName() : "Belirtilmemiş"));
            return;
        }

        List<Product> productsByName = productDAO.searchProductsByName(searchTerm);
        if (productsByName == null || productsByName.isEmpty()) {
            System.out.println("Aradığınız kritere uygun ürün bulunamadı.");
            return;
        }

        System.out.println("Aradığınız kritere uygun ürünler:");
        for (Product product : productsByName) {
            System.out.println("Ürün ID: " + product.getProductId() + " | Adı: " + product.getName() + " | Stok: " + product.getStock() + " | Kategori: " + (product.getCategory() != null ? product.getCategory().getName() : "Belirtilmemiş"));
        }
    }

    private static void sortProductsByStock() {
        System.out.println("\n--- Stok Seviyesine Göre Ürünleri Sırala ---");
        System.out.println("1. Artan Sıralama");
        System.out.println("2. Azalan Sıralama");
        int sortChoice = getIntegerInput("Seçiminizi yapın: ");

        boolean ascending;
        if (sortChoice == 1) {
            ascending = true;
        } else if (sortChoice == 2) {
            ascending = false;
        } else {
            System.out.println("Geçersiz seçim. Lütfen 1 veya 2 girin.");
            return;
        }

        List<Product> sortedProducts = productDAO.getProductsSortedByStock(ascending);
        if (sortedProducts == null || sortedProducts.isEmpty()) {
            System.out.println("Hiç ürün bulunmamaktadır.");
            return;
        }

        System.out.println("Ürünler stok seviyesine göre sıralandı:");
        for (Product product : sortedProducts) {
            System.out.println("Ürün ID: " + product.getProductId() + " | Adı: " + product.getName() + " | Stok: " + product.getStock() + " | Kategori: " + (product.getCategory() != null ? product.getCategory().getName() : "Belirtilmemiş"));
        }
    }

    private static void reportLowStockProducts() {
        System.out.println("\n--- Düşük Stoklu Ürünleri Raporla ---");
        int threshold = getIntegerInput("Minimum stok seviyesi (örn: 10): ");

        List<Product> lowStockProducts = productDAO.getLowStockProducts(threshold);
        if (lowStockProducts == null || lowStockProducts.isEmpty()) {
            System.out.println("Belirtilen eşik değerinin altında stok seviyesine sahip ürün bulunmamaktadır.");
            return;
        }

        System.out.println("Düşük stoklu ürünler:");
        for (Product product : lowStockProducts) {
            System.out.println("Ürün ID: " + product.getProductId() + " | Adı: " + product.getName() + " | Stok: " + product.getStock() + " | Kategori: " + (product.getCategory() != null ? product.getCategory().getName() : "Belirtilmemiş"));
        }
    }

    private static void addWarehouseLayout() {
        System.out.println("\n--- Depo Düzeni Ekle ---");
        String layoutName = getStringInput("Depo düzeni adı: ");

        WarehouseLayout layout = warehouseFactory.createWarehouseLayout(layoutName);

        warehouseLayoutDAO.addWarehouseLayout(layout);

        System.out.println("Depo düzeni eklendi: " + layout.getLayoutName());
    }

    private static void listWarehouseLayouts() {
        System.out.println("\n--- Tüm Depo Düzenleri ---");
        List<WarehouseLayout> layouts = warehouseLayoutDAO.getAllWarehouseLayouts();
        if (layouts == null || layouts.isEmpty()) {
            System.out.println("Hiç depo düzeni bulunmamaktadır.");
            return;
        }

        for (WarehouseLayout layout : layouts) {
            System.out.println("Depo Düzeni ID: " + layout.getId() + " | Adı: " + layout.getLayoutName());
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.print("Boş bırakamazsınız. " + prompt);
            input = scanner.nextLine().trim();
        }
        return input;
    }

    private static int getIntegerInput(String prompt) {
        int number = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                number = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz sayı. Lütfen tekrar deneyin.");
            }
        }
        return number;
    }

    private static Category selectCategory() {
        List<Category> categories = categoryDAO.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            System.out.println("Hiç kategori bulunmamaktadır.");
            return null;
        }

        System.out.println("Kategoriler:");
        for (Category category : categories) {
            System.out.println(category.getId() + ". " + category.getName());
        }

        int categoryId = getIntegerInput("Kategori ID'si seçin: ");
        Category selectedCategory = categoryDAO.getCategoryById(categoryId);
        if (selectedCategory == null) {
            System.out.println("Geçersiz kategori ID'si. Lütfen mevcut kategorilerden birini seçin.");
        }
        return selectedCategory;
    }


    private static Product selectProduct() {
        List<Product> products = productDAO.getAllProducts();
        if (products == null || products.isEmpty()) {
            System.out.println("Hiç ürün bulunmamaktadır.");
            return null;
        }

        System.out.println("Ürünler:");
        for (Product product : products) {
            System.out.println(product.getId() + ". " + product.getName() + " (Stok: " + product.getStock() + ")");
        }

        int productId = getIntegerInput("Ürün ID'si seçin: ");
        Product selectedProduct = productDAO.getProductById(productId);
        if (selectedProduct == null) {
            System.out.println("Geçersiz ürün ID'si. Lütfen mevcut ürünlerden birini seçin.");
        }
        return selectedProduct;
    }

    private static void assignProductToWarehouse() {
        System.out.println("\n--- Ürünü Depoya Ata ---");
        Product product = selectProduct();
        if (product == null) {
            System.out.println("Ürün seçilemedi.");
            return;
        }

        WarehouseLayout warehouseLayout = selectWarehouseLayout();
        if (warehouseLayout == null) {
            System.out.println("Depo düzeni seçilemedi.");
            return;
        }

        warehouseLayout.addProduct(product);
        productDAO.updateProduct(product);
        System.out.println("Ürün " + product.getName() + " deposu " + warehouseLayout.getLayoutName() + " olarak ayarlandı.");
    }

    private static void listProductsInWarehouse() {
        System.out.println("\n--- Depodaki Ürünleri Listele ---");
        WarehouseLayout warehouseLayout = selectWarehouseLayout();
        if (warehouseLayout == null) {
            System.out.println("Depo düzeni seçilemedi.");
            return;
        }

        warehouseLayout.displayLayout();
    }

    private static WarehouseLayout selectWarehouseLayout() {
        List<WarehouseLayout> layouts = warehouseLayoutDAO.getAllWarehouseLayouts();
        if (layouts == null || layouts.isEmpty()) {
            System.out.println("Hiç depo düzeni bulunmamaktadır.");
            return null;
        }

        System.out.println("Depo Düzenleri:");
        for (WarehouseLayout layout : layouts) {
            System.out.println(layout.getId() + ". " + layout.getLayoutName());
        }

        int layoutId = getIntegerInput("Depo düzeni ID'si seçin: ");
        WarehouseLayout selectedLayout = warehouseLayoutDAO.getWarehouseLayoutById(layoutId);
        if (selectedLayout == null) {
            System.out.println("Geçersiz depo düzeni ID'si.");
        }
        return selectedLayout;
    }

    private static void addSubCategory() {
        System.out.println("\n--- Alt Kategori Ekle ---");


        Category parentCategory = selectCategory();
        if (parentCategory == null) {
            System.out.println("Ana kategori seçilemedi. İşlem iptal edildi.");
            return;
        }

        String subCategoryName = getStringInput("Alt kategori adı: ");
        Category subCategory = new Category(subCategoryName);

        parentCategory.addSubCategory(subCategory);
        categoryDAO.updateCategory(parentCategory);

        System.out.println("Alt kategori eklendi: " + subCategoryName);
    }

    private static void listSubCategories() {
        System.out.println("\n--- Alt Kategorileri Listele ---");

        Category parentCategory = selectCategory();
        if (parentCategory == null) {
            System.out.println("Kategori seçilemedi. İşlem iptal edildi.");
            return;
        }

        System.out.println("Alt kategoriler:");
        for (Category subCategory : parentCategory.getSubCategories()) {
            System.out.println("- " + subCategory.getName() + " (ID: " + subCategory.getId() + ")");
        }
    }

    private static void changeParentCategory() {
        System.out.println("\n--- Parent Kategoriyi Değiştir ---");

        Category childCategory = selectCategory();
        if (childCategory == null) {
            System.out.println("Kategori seçilemedi. İşlem iptal edildi.");
            return;
        }

        System.out.println("Yeni parent kategoriyi seçin:");
        Category newParent = selectCategory();

        childCategory.setParent(newParent);
        categoryDAO.updateCategory(childCategory);
        System.out.println("Parent kategori güncellendi.");
    }

    private static void removeSubCategory() {
        System.out.println("\n--- Alt Kategoriyi Kaldır ---");
        Category parentCategory = selectCategory();
        if (parentCategory == null) {
            System.out.println("Üst kategori seçilemedi.");
            return;
        }

        List<Category> subCategories = parentCategory.getSubCategories();
        if (subCategories.isEmpty()) {
            System.out.println("Bu kategorinin alt kategorisi yok.");
            return;
        }

        System.out.println("Alt Kategoriler:");
        for (int i = 0; i < subCategories.size(); i++) {
            System.out.println((i + 1) + ". " + subCategories.get(i).getName());
        }

        int subCategoryIndex = getIntegerInput("Kaldırmak istediğiniz alt kategorinin numarasını seçin: ") - 1;
        if (subCategoryIndex < 0 || subCategoryIndex >= subCategories.size()) {
            System.out.println("Geçersiz seçim.");
            return;
        }

        Category subCategory = subCategories.get(subCategoryIndex);
        parentCategory.removeSubCategory(subCategory);

        System.out.println("Alt kategori başarıyla kaldırıldı: " + subCategory.getName());
    }

    private static void changeWarehouseLayoutName() {
        System.out.println("\n--- Depoda Düzeni İsmini Değiştir ---");
        WarehouseLayout warehouseLayout = selectWarehouseLayout();
        if (warehouseLayout == null) {
            System.out.println("Depo düzeni seçilemedi.");
            return;
        }

        String newName = getStringInput("Yeni isim: ");
        warehouseLayout.setLayoutName(newName);
        warehouseLayoutDAO.updateWarehouseLayout(warehouseLayout);

        System.out.println("Depo düzeni ismi güncellendi.");
    }

    private static void getWarehouseLayout() {
        System.out.println("\n--- Ait Olduğu Depo Düzenini Göster ---");
        Product product = selectProduct();
        if (product == null) {
            System.out.println("Ürün seçilemedi.");
            return;
        }

        WarehouseLayout layout = warehouseLayoutDAO.getWarehouseLayoutById(product.getWarehouseLayout().getId());
        if (layout == null) {
            System.out.println("Ürünün ait olduğu depo düzeni bulunamadı.");
        }
    }
}
