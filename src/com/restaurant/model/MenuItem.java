package com.restaurant.model;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private String category;

    public MenuItem(int id, String name, double price, String category) {
        this.id = id; this.name = name; this.price = price; this.category = category;
    }
    public MenuItem(String name, double price, String category) {
        this(0, name, price, category);
    }
    // getters & setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public void setPrice(double price) { this.price = price; }
}
