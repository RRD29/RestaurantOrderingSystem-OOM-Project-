package com.restaurant.util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    public static String readString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    public static int readInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.print("Enter a number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    public static double readDouble(String msg) {
        System.out.print(msg);
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a number: ");
            sc.next();
        }
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }
}
