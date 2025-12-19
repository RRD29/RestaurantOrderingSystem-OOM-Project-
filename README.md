# Restaurant Ordering System

A comprehensive Java-based restaurant management system with role-based access control, menu management, order processing, and reporting capabilities.

## Overview

This project is a desktop application for managing restaurant operations including menu management, order taking, customer management, and sales reporting. It features a graphical user interface built with Java Swing and uses MySQL for data persistence.

## Features

### Role-Based Access Control
- **Admin**: Full system access including user management, menu management, and reports
- **Waiter**: Order management, customer service, and basic menu viewing
- **Customer**: Menu browsing and order placement

### Core Functionality
- **Menu Management**: Add, update, delete, and categorize menu items
- **Order Processing**: Create, modify, and track orders with real-time status updates
- **Customer Management**: Register and manage customer information
- **Reporting**: Generate sales reports and order history
- **Table Management**: Assign orders to specific table numbers

### Menu Categories
- Main Course
- Desserts
- Snacks
- Beverages
- Roti
- Starters

## Tech Stack

- **Language**: Java 8+
- **GUI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **Build Tool**: Apache Maven
- **JDBC Driver**: MySQL Connector/J 8.0.33
- **Architecture**: MVC (Model-View-Controller)

## Project Structure

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
└── README.md                           # This file
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven 3.6+
- MySQL Server 8.0+
- MySQL Connector/J 8.0.33 (included in lib/)

## Database Setup

1. Install and start MySQL Server
2. Create a database named `restaurant`:
   ```sql
   CREATE DATABASE restaurant;
   ```
3. Run the schema script:
   ```bash
   mysql -u root -p restaurant < schema.sql
   ```

### Default Credentials
- **Database**: restaurant
- **Username**: root
- **Password**: Anurag@2005 (configured in `DBUtil.java`)

> **Note**: Update database credentials in `src/com/restaurant/util/DBUtil.java` if needed.

## Installation & Setup

1. **Clone or download the project**
2. **Navigate to the project directory**
3. **Compile the project**:
   ```bash
   mvn clean compile
   ```
4. **Run the application**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.restaurant.App"
   ```
   Or run directly:
   ```bash
   java -cp "bin:lib/*" com.restaurant.App
   ```

## Usage

### Starting the Application
Run `App.java` to launch the main menu. The application will display a login screen or main interface depending on the implementation.

### User Roles and Permissions

#### Admin User
- **Default Credentials**: username: `admin`, password: `admin123`
- Access to all system features
- User management
- Menu management
- System reports

#### Waiter
- Take customer orders
- View and update order status
- Access menu information

#### Customer
- Browse menu
- Place orders
- View order history

### Key Workflows

1. **Menu Management**:
   - Admin can add/edit/delete menu items
   - Items are categorized for easy browsing

2. **Order Processing**:
   - Waiter selects customer and table
   - Adds menu items to order
   - Calculates total automatically
   - Updates order status

3. **Reporting**:
   - Generate sales reports
   - View order history
   - Track revenue by category

## Database Schema

The system uses the following main tables:

- `users`: Role-based authentication
- `customers`: Customer information
- `menu_items`: Menu catalog with categories
- `orders`: Order headers with status tracking
- `order_items`: Order line items

See `schema.sql` for complete database structure and sample data.

## Development

### Building from Source
```bash
mvn clean package
```

### Running Tests
```bash
mvn test
```

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Include JavaDoc comments for public methods
- Handle exceptions appropriately

## Dependencies

- **MySQL Connector/J**: JDBC driver for MySQL connectivity
- **JUnit**: For unit testing (if configured)

## Configuration

### Database Configuration
Edit `src/com/restaurant/util/DBUtil.java` to update:
- Database URL
- Username
- Password

### Application Settings
Modify constants in respective service classes for:
- Default categories
- Status values
- Report formats

## Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Ensure MySQL server is running
   - Verify credentials in `DBUtil.java`
   - Check database exists and schema is applied

2. **Application Won't Start**
   - Ensure JDK 8+ is installed
   - Check classpath includes MySQL connector
   - Verify all dependencies are compiled

3. **GUI Issues**
   - Ensure system supports Java Swing
   - Check display settings for high DPI screens

### Logs
Application logs database connection attempts. Check console output for error messages.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make changes following the existing code style
4. Test thoroughly
5. Submit a pull request

## License

This project is for educational purposes. Please check individual component licenses.

## Support

For issues or questions:
1. Check the troubleshooting section
2. Review the code comments
3. Examine the database schema
4. Test with sample data provided in `schema.sql`

## Future Enhancements

- Web-based interface
- Mobile app integration
- Payment gateway integration
- Kitchen display system
- Inventory management
- Reservation system
- Multi-language support
