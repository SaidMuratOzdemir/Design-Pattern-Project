USE dpproject;

-- Kategoriler
INSERT INTO category (name, parent_id) VALUES ('Elektronik', NULL);
INSERT INTO category (name, parent_id) VALUES ('Bilgisayar', 1);
INSERT INTO category (name, parent_id) VALUES ('Cep Telefonu', 1);
INSERT INTO category (name, parent_id) VALUES ('Ev Aletleri', NULL);
INSERT INTO category (name, parent_id) VALUES ('Buzdolapları', 4);
INSERT INTO category (name, parent_id) VALUES ('Çamaşır Makineleri', 4);

-- Depo Düzenleri
INSERT INTO warehouse_layout (layoutName) VALUES ('Depo A');
INSERT INTO warehouse_layout (layoutName) VALUES ('Depo B');
INSERT INTO warehouse_layout (layoutName) VALUES ('Depo C');

-- Ürünler
INSERT INTO product (product_id, name, stock, category_id, warehouse_layout_id) VALUES ('P1001', 'Laptop', 50, 2, 1);
INSERT INTO product (product_id, name, stock, category_id, warehouse_layout_id) VALUES ('P1002', 'Akıllı Telefon', 100, 3, 1);
INSERT INTO product (product_id, name, stock, category_id, warehouse_layout_id) VALUES ('P2001', 'Buzdolabı Model A', 25, 5, 2);
INSERT INTO product (product_id, name, stock, category_id, warehouse_layout_id) VALUES ('P2002', 'Çamaşır Makinesi Model B', 30, 6, 3);
INSERT INTO product (product_id, name, stock, category_id, warehouse_layout_id) VALUES ('P3001', 'Tablet', 70, 2, NULL); -- Henüz bir depoya atanmamış
