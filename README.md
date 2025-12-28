# Restaurant Ordering System

A comprehensive Java-based desktop application for managing restaurant operations including menu management, order taking, customer management, and sales reporting. It feares a graphical user interface built with Java Swing and uses MySQL for data persistence.

---

## 1.Features

### 1.1 Role-Based Access Control
- **Admin**: Full system access including user management, menu management, and reports
- **Waiter**: Order management, customer service, and basic menu viewing
- **Customer**: Menu browsing and order placement

### 1.2 Core Functionality
- **Menu Management**: Add, update, delete, and categorize menu items
- **Order Processing**: Create, modify, and track orders with real-time status updates
- **Customer Management**: Register and manage customer information
- **Reporting**: Generate sales reports and order history
- **Table Management**: Assign orders to specific table numbers

### 1.3 Key Workflows

a. **Menu Management**:
   - Admin can add/edit/delete menu items
   - Items are categorized for easy browsing

b. **Order Processing**:
   - Waiter selects customer and table
   - Adds menu items to order
   - Calculates total automatically
   - Updates order status

c. **Reporting**:
   - Generate sales reports
   - View order history
   - Track revenue by category

---

## 2.Tech Stack

- **Language**: Java 8+
- **GUI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **Build Tool**: Apache Maven
- **JDBC Driver**: MySQL Connector/J 8.0.33
- **Architecture**: MVC (Model-View-Controller)

---

## 3.Project Structure

```
RestaurantOrderingSystem/
├── src/
│   ├── com/restaurant/
│   │   ├── App.java                    # Main application entry point
│   │   ├── model/                      # Data models
│   │   │   ├── Customer.java
│   │   │   ├── MenuItem.java
│   │   │   ├── Order.java
│   │   │   └── OrderItem.java
│   │   ├── service/                    # Business logic services
│   │   │   ├── MenuService.java
│   │   │   ├── OrderService.java
│   │   │   └── ReportService.java
│   │   ├── ui/                         # User interface classes
│   │   │   ├── MainMenu.java
│   │   │   ├── AdminUI.java
│   │   │   ├── WaiterUI.java
│   │   │   ├── MenuManagementUI.java
│   │   │   ├── MenuViewUI.java
│   │   │   ├── OrderUI.java
│   │   │   ├── TakeOrderUI.java
│   │   │   ├── ViewOrdersUI.java
│   │   │   ├── OrderHistoryUI.java
│   │   │   ├── SalesReportUI.java
│   │   │   └── ...
│   │   └── util/                       # Utility classes
│   │       ├── DBUtil.java
│   │       ├── FileUtil.java
│   │       └── InputUtil.java
├── bin/                                # Compiled class files
├── data/                               # Data files
│   ├── menu.txt
│   ├── orders.txt
│   └── sales_report.txt
├── lib/                                # External libraries
│   └── mysql-connector-j-8.0.33.jar
├── pom.xml                             # Maven configuration
├── schema.sql                          # Database schema
├── TODO.md                             # Project tasks
├── .gitignore
└── README.md                          

```
---

## 4.Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven 3.6+
- MySQL Server 8.0+
- MySQL Connector/J 8.0.33 (included in lib/)
---

## 5.Database Setup

- Install and start MySQL Server
- Create a database named `restaurant`:
   ```sql
   CREATE DATABASE restaurant;
   ```
- Run the schema script:
   ```bash
   mysql -u root -p restaurant < schema.sql
   ```
---
## 6.Configuration

### 6.1 Database Configuration
Edit `src/com/restaurant/util/DBUtil.java` to update:
- Database URL
- Username
- Password

### 6.2 Application Settings
Modify constants in respective service classes for:
- Default categories
- Status values
- Report formats
---
## 7.Future Enhancements

- Web-based interface
- Mobile app integration
- Payment gateway integration
- Kitchen display system
- Inventory management
- Reservation system
- Multi-language support
