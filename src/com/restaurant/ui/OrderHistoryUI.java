package com.restaurant.ui;

import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class OrderHistoryUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public OrderHistoryUI() {
        setTitle("Order History");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        model = new DefaultTableModel(new Object[]{"Order ID","Customer","Table","Total"}, 0){
            @Override public boolean isCellEditable(int row,int col){ return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        JButton viewItems = new JButton("View Selected Items");
        JPanel p = new JPanel(); p.add(refresh); p.add(viewItems);
        add(p, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadOrders());
        viewItems.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this,"Select an order"); return; }
            int orderId = (int) model.getValueAt(r,0);
            List<OrderItem> items = OrderService.getItemsForOrder(orderId);
            StringBuilder sb = new StringBuilder();
            for (OrderItem it : items)
                sb.append(it.getItemName()).append(" x").append(it.getQuantity()).append(" - ₹").append(it.getPrice()*it.getQuantity()).append("\n");
            JOptionPane.showMessageDialog(this,sb.length()==0 ? "No items": sb.toString(),"Order Items",JOptionPane.INFORMATION_MESSAGE);
        });

        loadOrders();
        setVisible(true);
    }

    private void loadOrders() {
        model.setRowCount(0);
        List<Order> orders = OrderService.getAllOrders();
        for (Order o : orders)
            model.addRow(new Object[]{o.getId(), o.getCustomerName(), o.getTableNumber(), o.getTotal()});
    }
}
