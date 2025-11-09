package com.restaurant.model;

import java.util.List;

public class Order {
    private int id;
    private String customerName;
    private int tableNumber;
    private double total;
    private String status;
    private List<OrderItem> items;

    public Order(String customerName, int tableNumber, List<OrderItem> items) {
        this.customerName = customerName; this.tableNumber = tableNumber; this.items = items;
        this.total = items.stream().mapToDouble(i -> i.getPrice()*i.getQuantity()).sum();
        this.status = "In Preparation";
    }

    public Order(int id, String customerName, int tableNumber, double total, String status) {
        this.id = id; this.customerName = customerName; this.tableNumber = tableNumber;
        this.total = total; this.status = status;
    }

    // getters
    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public int getTableNumber() { return tableNumber; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public List<OrderItem> getItems() { return items; }
}
