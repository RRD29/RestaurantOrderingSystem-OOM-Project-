package com.restaurant.util;

import java.io.*;
import java.util.*;

public class FileUtil {

    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) return lines;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } catch (IOException e) { e.printStackTrace(); }
        return lines;
    }

    public static void writeFile(String filename, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (String line : lines) pw.println(line);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void appendToFile(String filename, String line) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) {
            pw.println(line);
        } catch (IOException e) { e.printStackTrace(); }
    }
}

