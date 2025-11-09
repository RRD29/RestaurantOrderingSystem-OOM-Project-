package com.restaurant.ui;

import com.restaurant.model.MenuItem;
import com.restaurant.service.MenuService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuViewUI extends JFrame {
    public MenuViewUI() {
        setTitle("View Menu");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        Map<String, List<MenuItem>> itemsByCategory = MenuService.getItemsByCategory();

        for (String category : itemsByCategory.keySet()) {
            JPanel panel = new JPanel(new BorderLayout());
            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> list = new JList<>(listModel);
            list.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            for (MenuItem item : itemsByCategory.get(category)) {
                listModel.addElement(item.getName() + " - ₹" + item.getPrice());
            }

            panel.add(new JScrollPane(list), BorderLayout.CENTER);
            tabbedPane.addTab(category, panel);
        }

        add(tabbedPane);
        setVisible(true);
    }
}
