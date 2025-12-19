package com.restaurant.service;

import java.util.*;
import com.restaurant.util.FileUtil;

public class ReportService {
    public static double calculateTotalSales() {
        List<String> orders = FileUtil.readFile("data/orders.txt");
        double total = 0;
        for (String order : orders) {
            if (order.contains("Total: ₹")) {
                try {
                    String amount = order.split("Total: ₹")[1].trim();
                    total += Double.parseDouble(amount);
                } catch (Exception ignored) {}
            }
        }
        return total;
    }
}
