package com.restaurant.model;

public class OrderItem {
    private Integer id;
    private int orderId;
    private Integer menuId;
    private String itemName;
    private double price;
    private int quantity;

    public OrderItem(Integer menuId, String itemName, double price, int quantity) {
        this.menuId = menuId; this.itemName = itemName; this.price = price; this.quantity = quantity;
    }

    public OrderItem(int id, String itemName, double price, int quantity) {
        this.id = id; this.itemName = itemName; this.price = price; this.quantity = quantity;
    }

    public Integer getId() { return id; }
    public String getItemName() { return itemName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
