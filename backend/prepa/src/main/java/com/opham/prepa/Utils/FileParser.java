package com.opham.prepa.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
    public List<String> extractNames(String filePath) {
        List<String> names = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" -> ");
                if (parts.length >= 1) {
                    names.add(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return names;
    }

    public String recupererIP(String name, String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" -> ");
                if (parts.length == 2 && parts[0].equals(name)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si le nom n'a pas été trouvé
    }
}
