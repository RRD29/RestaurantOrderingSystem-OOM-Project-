package com.restaurant.ui;

import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.service.MenuService;
import com.restaurant.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaiterUI extends JFrame {
    private JTextField customerNameField, tableNumberField;
    private JTabbedPane tabbedPane;
    private Map<String, DefaultTableModel> categoryModels = new HashMap<>();
    private Map<String, JTable> categoryTables = new HashMap<>();
    private List<OrderItem> orderItems = new ArrayList<>();
    private JTable customerTable;
    private DefaultTableModel customerModel;
    private JButton newCustomerBtn;
    private JButton selectCustomerBtn;
    private JButton backToCustomersBtn;
    private JPanel customerPanel;
    private JPanel orderPanel;
    private Order selectedOrder;

    public WaiterUI() {
        setTitle("Waiter - Take Orders");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        // Customer selection panel
        customerPanel = new JPanel(new BorderLayout());
        customerModel = new DefaultTableModel(new Object[]{"Order ID","Customer Name","Table","Status"},0){
            @Override public boolean isCellEditable(int row,int col){ return false; }
        };
        customerTable = new JTable(customerModel);
        customerPanel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

        JPanel customerBtnPanel = new JPanel();
        newCustomerBtn = new JButton("New Customer");
        selectCustomerBtn = new JButton("Select Customer");
        customerBtnPanel.add(newCustomerBtn);
        customerBtnPanel.add(selectCustomerBtn);
        customerPanel.add(customerBtnPanel, BorderLayout.SOUTH);

        // Order panel (existing order taking UI)
        orderPanel = new JPanel(new BorderLayout());

        // Customer info
        JPanel topPanel = new JPanel(new GridLayout(1,4,10,10));
        topPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        topPanel.add(customerNameField);
        topPanel.add(new JLabel("Table Number:"));
        tableNumberField = new JTextField();
        topPanel.add(tableNumberField);
        orderPanel.add(topPanel, BorderLayout.NORTH);

        // Menu with tabs for categories
        tabbedPane = new JTabbedPane();

        // Define categories
        String[] categories = {"Main Course", "Dessert", "Snacks", "Beverage", "Roti", "Starters"};

        for (String cat : categories) {
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID","Name","Price","Quantity"},0){
                @Override public boolean isCellEditable(int row,int col){ return col==3; }
            };
            JTable table = new JTable(model);
            categoryModels.put(cat, model);
            categoryTables.put(cat, table);
            tabbedPane.addTab(cat, new JScrollPane(table));
        }

        orderPanel.add(tabbedPane, BorderLayout.CENTER);

        // Buttons
        JPanel bottomPanel = new JPanel();
        JButton placeOrderBtn = new JButton("Place Order");
        JButton clearBtn = new JButton("Clear");
        JButton refreshBtn = new JButton("Refresh Menu");
        JButton viewMenuBtn = new JButton("View Menu");
        backToCustomersBtn = new JButton("Back to Customers");
        bottomPanel.add(placeOrderBtn);
        bottomPanel.add(clearBtn);
        bottomPanel.add(refreshBtn);
        bottomPanel.add(viewMenuBtn);
        bottomPanel.add(backToCustomersBtn);
        orderPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Start with customer panel
        add(customerPanel, BorderLayout.CENTER);
        loadCustomers();
        loadMenu();

        // Button listeners
        newCustomerBtn.addActionListener(e -> switchToNewCustomer());
        selectCustomerBtn.addActionListener(e -> selectCustomer());
        backToCustomersBtn.addActionListener(e -> switchToCustomerPanel());

        refreshBtn.addActionListener(e -> loadMenu());

        clearBtn.addActionListener(e -> {
            customerNameField.setText("");
            tableNumberField.setText("");
            for (DefaultTableModel model : categoryModels.values()) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    model.setValueAt(0, i, 3);
                }
            }
        });

        placeOrderBtn.addActionListener(e -> placeOrder());

        viewMenuBtn.addActionListener(e -> new MenuViewUI().setVisible(true));

        setVisible(true);
    }

    private void loadMenu(){
        // Clear all category models
        for (DefaultTableModel model : categoryModels.values()) {
            model.setRowCount(0);
        }

        Map<String, List<MenuItem>> itemsByCategory = MenuService.getItemsByCategory();
        for (String category : itemsByCategory.keySet()) {
            DefaultTableModel model = categoryModels.get(category);
            if (model != null) {
                for (MenuItem m : itemsByCategory.get(category)) {
                    model.addRow(new Object[]{m.getId(), m.getName(), m.getPrice(), 0});
                }
            }
        }
    }

    private void placeOrder(){
        // Stop any ongoing cell editing to commit changes
        for (JTable table : categoryTables.values()) {
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
        }

        String customerName = customerNameField.getText().trim();
        String tableNumberStr = tableNumberField.getText().trim();
        if(customerName.isEmpty() || tableNumberStr.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter customer name and table number");
            return;
        }

        int tableNumber;
        try { tableNumber = Integer.parseInt(tableNumberStr); }
        catch(NumberFormatException e){ JOptionPane.showMessageDialog(this,"Table number must be numeric"); return; }

        orderItems.clear();
        for (DefaultTableModel model : categoryModels.values()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                int qty = 0;
                Object val = model.getValueAt(i, 3);
                if (val instanceof Integer) qty = (Integer) val;
                else if (val instanceof String) {
                    try { qty = Integer.parseInt((String) val); } catch (Exception ignored) {}
                }
                if (qty > 0) {
                    int id = (int) model.getValueAt(i, 0);
                    String name = (String) model.getValueAt(i, 1);
                    double price = (double) model.getValueAt(i, 2);
                    orderItems.add(new OrderItem(id, name, price, qty));
                }
            }
        }

        if(orderItems.isEmpty()){
            JOptionPane.showMessageDialog(this,"No items selected");
            return;
        }

        try{
            if(selectedOrder != null){
                // Add to existing order
                boolean success = OrderService.addItemsToOrder(selectedOrder.getId(), orderItems);
                if(success){
                    JOptionPane.showMessageDialog(this,"Items added to existing order successfully!");
                    clearOrderInputs();
                } else JOptionPane.showMessageDialog(this,"Failed to add items to order");
            } else {
                // Create new order
                Order order = new Order(customerName, tableNumber, orderItems);
                double gst = order.getTotal()*0.05;
                double totalWithGst = order.getTotal()+gst;
                int orderId = OrderService.createOrder(order);
                if(orderId!=-1){
                    showReceipt(order,totalWithGst,gst);
                    clearOrderInputs();
                } else JOptionPane.showMessageDialog(this,"Failed to place order");
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Error saving order: "+e.getMessage());
            e.printStackTrace();
        }
    }

    private void showReceipt(Order order,double totalWithGst,double gst){
        StringBuilder sb = new StringBuilder();
        sb.append("Customer: ").append(order.getCustomerName()).append("\n");
        sb.append("Table: ").append(order.getTableNumber()).append("\n");
        sb.append("-------------------------------\n");
        sb.append(String.format("%-20s %-5s %-7s\n","Item","Qty","Price"));
        for(OrderItem it: order.getItems())
            sb.append(String.format("%-20s %-5d %-7.2f\n",it.getItemName(),it.getQuantity(),it.getPrice()*it.getQuantity()));
        sb.append("-------------------------------\n");
        sb.append(String.format("Subtotal: %.2f\n",order.getTotal()));
        sb.append(String.format("GST(5%%): %.2f\n",gst));
        sb.append(String.format("Total: %.2f\n",totalWithGst));
        JOptionPane.showMessageDialog(this,sb.toString(),"Receipt",JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearOrderInputs(){
        customerNameField.setText("");
        tableNumberField.setText("");
        for (DefaultTableModel model : categoryModels.values()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(0, i, 3);
            }
        }
        orderItems.clear();
    }

    private void loadCustomers(){
        customerModel.setRowCount(0);
        List<Order> activeOrders = OrderService.getActiveOrders();
        for(Order o : activeOrders){
            customerModel.addRow(new Object[]{o.getId(), o.getCustomerName(), o.getTableNumber(), o.getStatus()});
        }
    }

    private void switchToNewCustomer(){
        selectedOrder = null;
        customerNameField.setText("");
        tableNumberField.setText("");
        customerNameField.setEditable(true);
        tableNumberField.setEditable(true);
        switchToOrderPanel();
    }

    private void selectCustomer(){
        int r = customerTable.getSelectedRow();
        if(r == -1){
            JOptionPane.showMessageDialog(this, "Select a customer");
            return;
        }
        int orderId = (int) customerModel.getValueAt(r, 0);
        String customerName = (String) customerModel.getValueAt(r, 1);
        int tableNumber = (int) customerModel.getValueAt(r, 2);
        selectedOrder = new Order(orderId, customerName, tableNumber, 0, ""); // dummy order for reference
        customerNameField.setText(customerName);
        tableNumberField.setText(String.valueOf(tableNumber));
        customerNameField.setEditable(false);
        tableNumberField.setEditable(false);
        switchToOrderPanel();
    }

    private void switchToOrderPanel(){
        remove(customerPanel);
        add(orderPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void switchToCustomerPanel(){
        remove(orderPanel);
        add(customerPanel, BorderLayout.CENTER);
        loadCustomers();
        revalidate();
        repaint();
    }
}
