package com.restaurant.ui;

import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewOrdersUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public ViewOrdersUI() {
        setTitle("All Orders");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        model = new DefaultTableModel(new Object[]{"Order ID","Customer","Table","Total","Status"},0){
            @Override public boolean isCellEditable(int row,int col){ return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        JButton viewItems = new JButton("View Items");
        JButton markCompleted = new JButton("Mark Completed");
        JPanel panel = new JPanel();
        panel.add(refresh); panel.add(viewItems); panel.add(markCompleted);
        add(panel, BorderLayout.SOUTH);

        refresh.addActionListener(e -> loadOrders());
        viewItems.addActionListener(e -> {
            int r = table.getSelectedRow();
            if(r==-1){ JOptionPane.showMessageDialog(this,"Select order"); return; }
            int orderId = (int) model.getValueAt(r,0);
            List<OrderItem> items = OrderService.getItemsForOrder(orderId);
            StringBuilder sb = new StringBuilder();
            for(OrderItem i : items)
                sb.append(i.getItemName()).append(" x").append(i.getQuantity()).append(" - ₹").append(i.getPrice()*i.getQuantity()).append("\n");
            JOptionPane.showMessageDialog(this, sb.length()==0?"No items":sb.toString(), "Order Items", JOptionPane.INFORMATION_MESSAGE);
        });

        markCompleted.addActionListener(e -> {
            int r = table.getSelectedRow();
            if(r==-1){ JOptionPane.showMessageDialog(this,"Select order"); return; }
            int orderId = (int) model.getValueAt(r,0);
            try{
                // Update status to Completed
                com.restaurant.util.DBUtil.getConnection()
                        .prepareStatement("UPDATE orders SET status='Completed' WHERE id="+orderId)
                        .executeUpdate();
                loadOrders();
            } catch(Exception ex){ ex.printStackTrace(); }
        });

        loadOrders();
        setVisible(true);
    }

    private void loadOrders() {
        model.setRowCount(0);
        List<Order> orders = OrderService.getAllOrders();
        for(Order o : orders)
            model.addRow(new Object[]{o.getId(), o.getCustomerName(), o.getTableNumber(), o.getTotal(), o.getStatus()});
    }
}

