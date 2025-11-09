package com.restaurant.ui;

import javax.swing.*;
import java.awt.*;
import com.restaurant.service.ReportService;

public class SalesReportUI extends JFrame {
    public SalesReportUI() {
        setTitle("Sales Report");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        double total = ReportService.calculateTotalSales();
        JLabel label = new JLabel("Total Sales: ₹" + total, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(label);
    }
}
