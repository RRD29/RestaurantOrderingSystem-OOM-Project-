package com.restaurant.ui;

import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.service.MenuService;
import com.restaurant.service.OrderService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderUI extends JFrame {
    public OrderUI() {
        setTitle("Place Order");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8,8));

        List<MenuItem> menuList = MenuService.getAll();
        JPanel panel = new JPanel(new GridLayout(menuList.size() + 3, 3, 10, 10));

        panel.add(new JLabel("Item"));
        panel.add(new JLabel("Price"));
        panel.add(new JLabel("Quantity"));

        List<JTextField> qtyFields = new ArrayList<>();
        for (MenuItem item : menuList) {
            panel.add(new JLabel(item.getName()));
            panel.add(new JLabel(String.valueOf(item.getPrice())));
            JTextField qtyF = new JTextField("0");
            qtyFields.add(qtyF);
            panel.add(qtyF);
        }

        JButton placeOrder = new JButton("Place Order");
        panel.add(new JLabel("")); // empty cell
        panel.add(placeOrder);

        add(panel, BorderLayout.CENTER);

        placeOrder.addActionListener(e -> {
            List<OrderItem> items = new ArrayList<>();
            double total = 0;
            for (int i = 0; i < menuList.size(); i++) {
                int qty;
                try { qty = Integer.parseInt(qtyFields.get(i).getText().trim()); }
                catch (NumberFormatException ex) { qty = 0; }
                if (qty > 0) {
                    MenuItem m = menuList.get(i);
                    items.add(new OrderItem(m.getId(), m.getName(), m.getPrice(), qty));
                    total += m.getPrice() * qty;
                }
            }
            if (items.isEmpty()) { JOptionPane.showMessageDialog(this,"Select at least 1 item"); return; }

            String customer = JOptionPane.showInputDialog(this,"Customer Name:");
            if (customer == null || customer.trim().isEmpty()) customer = "Guest";
            int table = 1; // You can prompt for table number too
            Order order = new Order(customer, table, items);
            try {
                int orderId = OrderService.createOrder(order);
                JOptionPane.showMessageDialog(this,"Order placed! ID: "+orderId+"\nTotal: ₹"+total);
            } catch (SQLException ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this,"Order failed: "+ex.getMessage()); }
        });

        setVisible(true);
    }
}
