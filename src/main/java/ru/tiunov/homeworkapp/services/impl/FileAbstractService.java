package ru.tiunov.homeworkapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.InputStreamResource;

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

    protected InputStreamResource getInputStreamOfFile(String path) throws FileNotFoundException {
        File file = getFile(path);
        return new InputStreamResource(new FileInputStream(file));
    }

    protected void importFileFromInputStream(InputStream inputStream, String path) throws JsonProcessingException {
        StringBuffer jsonStringBuffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonStringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        validJson(jsonStringBuffer.toString());
        write(jsonStringBuffer.toString(), path);
    }

    private void validJson(String json) throws JsonProcessingException {
        new ObjectMapper().readTree(json);
    }

    private File getFile(String path) throws FileNotFoundException {
        if (Files.exists(Path.of(path))) {
            File file = new File(path);
            return file;
        }
        throw new FileNotFoundException();
    }
}
