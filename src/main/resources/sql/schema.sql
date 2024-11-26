-- Depo Düzeni Tablosu (Önce bağımsız tablo)
CREATE TABLE IF NOT EXISTS warehouse_layout (
                                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                                 layoutName VARCHAR(100) NOT NULL UNIQUE
);

-- Kategori Tablosu
CREATE TABLE IF NOT EXISTS category (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL UNIQUE,
                                          parent_id INT,
                                          FOREIGN KEY (parent_id) REFERENCES category(id) ON DELETE SET NULL
);

-- Ürün Tablosu (Son olarak, bağımlı tablo)
CREATE TABLE IF NOT EXISTS product (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        product_id VARCHAR(50) NOT NULL UNIQUE,
                                        name VARCHAR(100) NOT NULL,
                                        stock INT NOT NULL,
                                        category_id INT,
                                        warehouse_layout_id INT,
                                        FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL,
                                        FOREIGN KEY (warehouse_layout_id) REFERENCES warehouse_layout(id) ON DELETE SET NULL
);
