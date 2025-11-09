package com.restaurant.ui;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame {
    public AdminUI() {
        setTitle("Admin Panel");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("Admin Panel", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(heading, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton manageMenu = new JButton("Manage Menu");
        JButton viewMenu = new JButton("View Menu");
        JButton viewOrders = new JButton("View Orders");
        JButton orderHistory = new JButton("Order History");
        JButton salesReport = new JButton("Sales Report");
        JButton exit = new JButton("Exit");

        buttonPanel.add(manageMenu);
        buttonPanel.add(viewMenu);
        buttonPanel.add(viewOrders);
        buttonPanel.add(orderHistory);
        buttonPanel.add(salesReport);
        buttonPanel.add(exit);

        add(buttonPanel, BorderLayout.CENTER);

        manageMenu.addActionListener(e -> new MenuManagementUI().setVisible(true));
        viewMenu.addActionListener(e -> new MenuViewUI().setVisible(true));
        viewOrders.addActionListener(e -> new ViewOrdersUI().setVisible(true));
        orderHistory.addActionListener(e -> new OrderHistoryUI().setVisible(true));
        salesReport.addActionListener(e -> new SalesReportUI().setVisible(true));
        exit.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new AdminUI());
    }
}

