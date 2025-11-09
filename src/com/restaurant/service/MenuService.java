package com.restaurant.service;

import com.restaurant.model.MenuItem;
import com.restaurant.util.DBUtil;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class MenuService {

    // Get all menu items from DB
    public static List<MenuItem> getAll() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT id, name, price, category FROM menu_items ORDER BY category, name";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get items grouped by category
    public static Map<String, List<MenuItem>> getItemsByCategory() {
        List<MenuItem> allItems = getAll();
        return allItems.stream()
                .collect(Collectors.groupingBy(MenuItem::getCategory));
    }

    // Add new menu item
    public static MenuItem add(String name, double price, String category) throws SQLException {
        String sql = "INSERT INTO menu_items (name, price, category) VALUES (?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, category);

            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Item added to DB: " + name);

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return new MenuItem(keys.getInt(1), name, price, category);
                }
            }
        }
        return null;
    }

    // Update an existing menu item
    public static void updateItem(int id, String name, double price, String category) throws SQLException {
        String sql = "UPDATE menu_items SET name = ?, price = ?, category = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, category);
            ps.setInt(4, id);
            ps.executeUpdate();
        }
    }

    // Update price of an existing menu item
    public static void updatePrice(int id, double price) throws SQLException {
        String sql = "UPDATE menu_items SET price = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // Delete a menu item
    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM menu_items WHERE id = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
