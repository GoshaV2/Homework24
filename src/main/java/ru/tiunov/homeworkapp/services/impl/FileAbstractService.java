package ru.tiunov.homeworkapp.services.impl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileAbstractService {
    private void createFileIfNotExist(String path) {
        try {
            Path pathObject = Path.of(path);
            if (!Files.exists(pathObject)) {
                Files.createFile(pathObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void write(String json, String path) {
        clearFile(path);
        createFileIfNotExist(path);
        File file = new File(path);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String read(String path) {
        createFileIfNotExist(path);
        File file = new File(path);
        StringBuffer result = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
