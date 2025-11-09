package com.restaurant;

import com.restaurant.ui.MainMenu;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}
