package com.restaurant.ui;

import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.service.MenuService;
import com.restaurant.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TakeOrderUI extends JFrame {
    private JTextField customerNameField;
    private JTextField tableNumberField;
    private JTable menuTable;
    private DefaultTableModel model;

    public TakeOrderUI() {
        setTitle("Take Order");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        // Top panel: customer info
        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField(10);
        top.add(customerNameField);
        top.add(new JLabel("Table Number:"));
        tableNumberField = new JTextField(5);
        top.add(tableNumberField);
        add(top, BorderLayout.NORTH);

        // Menu table
        model = new DefaultTableModel(new Object[]{"ID","Name","Price","Category"}, 0){
            @Override public boolean isCellEditable(int row,int col){ return false; }
        };
        menuTable = new JTable(model);
        menuTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        add(new JScrollPane(menuTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel();
        JButton placeOrderBtn = new JButton("Place Order");
        JButton refreshBtn = new JButton("Refresh Menu");
        buttons.add(placeOrderBtn);
        buttons.add(refreshBtn);
        add(buttons, BorderLayout.SOUTH);

        // Load menu items
        loadMenuItems();

        // Refresh menu
        refreshBtn.addActionListener(e -> loadMenuItems());

        // Place order action
        placeOrderBtn.addActionListener(e -> placeOrder());

        setVisible(true);
    }

    private void loadMenuItems() {
        model.setRowCount(0);
        List<MenuItem> menu = MenuService.getAll();
        for(MenuItem m : menu){
            model.addRow(new Object[]{m.getId(), m.getName(), m.getPrice(), m.getCategory()});
        }
    }

    private void placeOrder() {
        String customerName = customerNameField.getText().trim();
        String tableStr = tableNumberField.getText().trim();

        if(customerName.isEmpty() || tableStr.isEmpty()){
            JOptionPane.showMessageDialog(this, "Customer name and table number required");
            return;
        }

        int tableNumber;
        try {
            tableNumber = Integer.parseInt(tableStr);
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Table number must be numeric");
            return;
        }

        int[] selectedRows = menuTable.getSelectedRows();
if(selectedRows.length == 0){
    JOptionPane.showMessageDialog(this, "No item selected!");
    return;
}

List<OrderItem> items = new ArrayList<>();
for(int r : selectedRows){
    int id = Integer.parseInt(model.getValueAt(r,0).toString());
    String name = model.getValueAt(r,1).toString();
    double price = Double.parseDouble(model.getValueAt(r,2).toString());

    String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity for " + name, "1");
    if(qtyStr == null || qtyStr.isEmpty()) continue;

    int qty;
    try {
        qty = Integer.parseInt(qtyStr);
    } catch(NumberFormatException ex){
        JOptionPane.showMessageDialog(this, "Quantity must be numeric. Skipping " + name);
        continue;
    }

    items.add(new OrderItem(id, name, price, qty));
}


        if(items.isEmpty()){
            JOptionPane.showMessageDialog(this, "No valid items to place order!");
            return;
        }

        try {
            Order order = new Order(customerName, tableNumber, items);
            int orderId = OrderService.createOrder(order);
            JOptionPane.showMessageDialog(this, "Order placed successfully! Order ID: " + orderId);

            // Clear fields & selection
            customerNameField.setText("");
            tableNumberField.setText("");
            menuTable.clearSelection();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error placing order: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
    // Launch the Waiter panel
    javax.swing.SwingUtilities.invokeLater(() -> new TakeOrderUI());
}

}
