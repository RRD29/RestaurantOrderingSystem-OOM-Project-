package com.restaurant.ui;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame {
    public AdminUI() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,1,8,8));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("Admin Panel", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(heading);

        JButton manageMenu = new JButton("Manage Menu");
        JButton viewMenu = new JButton("View Menu");
        JButton viewOrders = new JButton("View Orders");
        JButton exit = new JButton("Exit");
        add(manageMenu);
        add(viewMenu);
        add(viewOrders);
        add(exit);

        manageMenu.addActionListener(e -> new MenuManagementUI().setVisible(true));
        viewMenu.addActionListener(e -> new MenuViewUI().setVisible(true));
        viewOrders.addActionListener(e -> new ViewOrdersUI().setVisible(true));
        exit.addActionListener(e -> dispose());

        setVisible(true);
    }
    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> new AdminUI());
}

}

