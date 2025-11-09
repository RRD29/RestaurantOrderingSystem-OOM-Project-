-- Users table for role-based authentication
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('CUSTOMER', 'WAITER', 'ADMIN') NOT NULL DEFAULT 'CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Customers table for customer management
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    mobile VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE menu_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    availability BOOLEAN DEFAULT TRUE,
    image_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
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

CREATE TABLE order_items (
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

-- Insert default admin user
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');

-- Insert sample menu items
INSERT INTO menu_items (name, category, description, price, availability) VALUES
('Margherita Pizza', 'Pizza', 'Classic cheese pizza with tomato sauce', 250.00, true),
('Pepperoni Pizza', 'Pizza', 'Spicy pepperoni with cheese', 350.00, true),
('Chicken Burger', 'Snacks', 'Grilled chicken burger with fries', 180.00, true),
('French Fries', 'Snacks', 'Crispy golden fries', 80.00, true),
('Coca Cola', 'Beverages', 'Refreshing cola drink', 50.00, true),
('Ice Cream', 'Desserts', 'Vanilla ice cream scoop', 60.00, true);
