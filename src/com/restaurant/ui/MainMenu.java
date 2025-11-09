package com.restaurant.ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Restaurant Ordering System");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,1,10,10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton waiterBtn = new JButton("Waiter");
        JButton adminBtn = new JButton("Admin");
        JButton exitBtn = new JButton("Exit");

        add(waiterBtn);
        add(adminBtn);
        add(exitBtn);

        // Open Waiter UI
        waiterBtn.addActionListener(e -> new WaiterUI().setVisible(true));

        // Open Admin UI
        adminBtn.addActionListener(e -> new AdminUI().setVisible(true));

        // Exit app
        exitBtn.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
