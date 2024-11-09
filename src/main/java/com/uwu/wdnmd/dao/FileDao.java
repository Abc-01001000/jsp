package com.uwu.wdnmd.dao;

import com.uwu.wdnmd.framework.ModelAndView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileDao {

    public static String readFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        StringBuilder fileContent = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            return fileContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean writeFile(String path, String content) {
        File file = new File(path);
        try {
            if (!file.exists())
                file.createNewFile();
            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.write(content);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
