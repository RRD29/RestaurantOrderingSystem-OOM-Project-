CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('CUSTOMER', 'WAITER', 'ADMIN') NOT NULL DEFAULT 'CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    mobile VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS menu_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category ENUM('Main Course','Desserts','Snacks','Beverages','Roti','Starters') NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    availability BOOLEAN DEFAULT TRUE,
    image_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    customer_name VARCHAR(100),
    table_number INT,
    waiter_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) DEFAULT 0.00,
    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    payment_status ENUM('PENDING', 'PAID', 'REFUNDED') DEFAULT 'PENDING',
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (waiter_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    menu_id INT,
    item_name VARCHAR(100),
    quantity INT DEFAULT 1,
    price DECIMAL(10,2),
    subtotal DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu_items(id)
);

INSERT IGNORE INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');

-- Insert sample menu items
INSERT IGNORE INTO menu_items (name, category, description, price, availability) VALUES
('Margherita Pizza', 'Main Course', 'Classic cheese pizza with tomato sauce', 250.00, TRUE),
('Pepperoni Pizza', 'Main Course', 'Spicy pepperoni with cheese', 350.00, TRUE),
('Chicken Burger', 'Snacks', 'Grilled chicken burger with fries', 180.00, TRUE),
('French Fries', 'Snacks', 'Crispy golden fries', 80.00, TRUE),
('Coca Cola', 'Beverages', 'Refreshing cola drink', 50.00, TRUE),
('Ice Cream', 'Desserts', 'Vanilla ice cream scoop', 60.00, TRUE),
('Butter Roti', 'Roti', 'Soft Indian flatbread', 20.00, TRUE),
('Paneer Tikka', 'Starters', 'Spiced paneer cubes grilled', 150.00, TRUE);
