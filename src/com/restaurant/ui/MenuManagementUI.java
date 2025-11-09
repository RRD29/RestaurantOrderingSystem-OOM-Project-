package com.restaurant.ui;

import com.restaurant.model.MenuItem;
import com.restaurant.service.MenuService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MenuManagementUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public MenuManagementUI() {
        setTitle("Manage Menu");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8,8));

        // Table model
        model = new DefaultTableModel(new Object[]{"ID","Name","Price","Category"}, 0){
            @Override public boolean isCellEditable(int row,int col){ return false; }
        };
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons panel
        JPanel controls = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");
        controls.add(addBtn);
        controls.add(editBtn);
        controls.add(deleteBtn);
        controls.add(refreshBtn);
        add(controls, BorderLayout.SOUTH);

        // -------------------- BUTTON ACTIONS --------------------

        // Refresh table
        refreshBtn.addActionListener(e -> loadData());

        // Add new menu item
        addBtn.addActionListener(e -> {
            JTextField nameF = new JTextField();
            JTextField priceF = new JTextField();
            JTextField catF = new JTextField();
            Object[] msg = {"Name:", nameF, "Price:", priceF, "Category:", catF};
            int res = JOptionPane.showConfirmDialog(this, msg, "Add Menu Item", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String name = nameF.getText().trim();
                String priceStr = priceF.getText().trim();
                String category = catF.getText().trim();

                if(name.isEmpty() || priceStr.isEmpty() || category.isEmpty()){
                    JOptionPane.showMessageDialog(this,"All fields are required");
                    return;
                }

                try{
                    double price = Double.parseDouble(priceStr);

                    // Insert into DB
                    MenuService.add(name, price, category);

                    // Refresh table immediately
                    loadData();

                    JOptionPane.showMessageDialog(this,"Item added successfully!");
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this,"Price must be numeric");
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Error adding item: "+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Edit item
        editBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this,"Select a row"); return; }
            int id = (int) model.getValueAt(r, 0);
            String currentName = (String) model.getValueAt(r,1);
            String currentPrice = String.valueOf(model.getValueAt(r,2));
            String currentCategory = (String) model.getValueAt(r,3);

            JTextField nameF = new JTextField(currentName);
            JTextField priceF = new JTextField(currentPrice);
            JTextField catF = new JTextField(currentCategory);
            Object[] msg = {"Name:", nameF, "Price:", priceF, "Category:", catF};
            int res = JOptionPane.showConfirmDialog(this, msg, "Edit Menu Item", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String name = nameF.getText().trim();
                String priceStr = priceF.getText().trim();
                String category = catF.getText().trim();

                if(name.isEmpty() || priceStr.isEmpty() || category.isEmpty()){
                    JOptionPane.showMessageDialog(this,"All fields are required");
                    return;
                }

                try{
                    double price = Double.parseDouble(priceStr);
                    MenuService.updateItem(id, name, price, category);
                    loadData();
                    JOptionPane.showMessageDialog(this,"Item updated successfully!");
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this,"Price must be numeric");
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(this,"Error updating item: "+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Delete item
        deleteBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this,"Select a row"); return; }
            int id = (int) model.getValueAt(r, 0);
            int ok = JOptionPane.showConfirmDialog(this, "Delete item id "+id+"?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                try {
                    MenuService.delete(id);
                    loadData();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Delete failed: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Load initial data
        loadData();

        setVisible(true);
    }

    // -------------------- LOAD DATA FROM DB --------------------
    private void loadData() {
        model.setRowCount(0);
        List<MenuItem> items = MenuService.getAll();
        for (MenuItem m : items) {
            model.addRow(new Object[]{m.getId(), m.getName(), m.getPrice(), m.getCategory()});
        }
    }
}
