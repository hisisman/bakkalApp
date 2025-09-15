-- ==============================
-- Örnek Ürünler
-- ==============================
INSERT INTO products (name, description, price, stock_quantity, image_url)
VALUES ('Elma', 'Yeşil elma, taze ve lezzetli', 10.0, 100, '/images/apple.jpg');

INSERT INTO products (name, description, price, stock_quantity, image_url)
VALUES ('Armut', 'Sarı armut, tatlı ve sulu', 12.0, 50, '/images/armut.png');

INSERT INTO products (name, description, price, stock_quantity, image_url)
VALUES ('Muz', 'Sarı muz, olgun', 15.0, 30, '/images/banana.jpeg');

INSERT INTO products (name, description, price, stock_quantity, image_url)
VALUES ('Çilek', 'Taze çilek, paket 500g', 25.0, 20, '/images/strawberry.jpg');

INSERT INTO products (name, description, price, stock_quantity, image_url)
VALUES ('Domates', 'Kırmızı domates, kg', 8.0, 200, '/images/tomato.jpeg');

-- ==============================
-- Örnek Müşteriler
-- ==============================
INSERT INTO customers (name, email, phone_number) VALUES
                                                      ('Ali Veli', 'ali.veli@example.com', '5551112233'),
                                                      ('Ayşe Yılmaz', 'ayse.yilmaz@example.com', '5552223344'),
                                                      ('Mehmet Demir', 'mehmet.demir@example.com', '5553334455');

-- ==============================
-- Örnek Kullanıcılar (Login)
-- password = 1234 (plain text, Spring Security encode edilmemiş)
-- ==============================
--INSERT INTO users (name, email, password, phone_number, username, role) VALUES
  --                                                                          ('Admin User', 'admin@example.com', '1234', '5550001111', 'admin', 'ADMIN'),
    --                                                                        ('Normal User', 'user@example.com', '1234', '5550002222', 'user', 'USER');

-- ==============================
-- Örnek Siparişler
-- ==============================
INSERT INTO orders (total_amount, customer_id, order_date) VALUES
                                                               (50.0, 1, CURRENT_TIMESTAMP),
                                                               (120.0, 2, CURRENT_TIMESTAMP),
                                                               (30.0, 3, CURRENT_TIMESTAMP);
