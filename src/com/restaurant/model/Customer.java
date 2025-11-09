package com.restaurant.model;

public class Customer {
    private String name;
    private int tableNumber;

    public Customer(String name, int tableNumber) {
        this.name = name;
        this.tableNumber = tableNumber;
    }

    public String getName() { return name; }
    public int getTableNumber() { return tableNumber; }

    @Override
    public String toString() {
        return "Customer: " + name + " (Table " + tableNumber + ")";
    }
}
