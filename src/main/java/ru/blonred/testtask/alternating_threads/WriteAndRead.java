package ru.blonred.testtask.alternating_threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class WriteAndRead {
    private final String pathFile;

    public WriteAndRead() {
        pathFile = (getPathToResources() + "out.txt");
        writeContent(0);
    }

    synchronized public int[] processForThread(int targetNumber) {
        String readContent = readContent();
        int newValue = -1;
        int oldValue  = 0;
        if (readContent == null) {
            newValue = 0;
            writeContent(newValue);
        } else if ((oldValue = Integer.parseInt(readContent)) < targetNumber) {
            newValue = oldValue + 1;
            writeContent(newValue);
        }
        return new int[]{oldValue, newValue};
    }

    public String getPathToResources() {
        String path = WriteAndRead.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        String[] pathSplit = decodedPath.split("target");
        String pathToBeginDir = pathSplit[0];
        return (pathToBeginDir.replace("/", "\\") + "src\\main\\resources\\");
    }
    synchronized String readContent() {
        String buffer;
        String content = null;
        try (BufferedReader contentReader = new BufferedReader(new FileReader(pathFile))) {
            do {
                buffer = contentReader.readLine();
                if (buffer != null) {
                    content = buffer;
                }
            } while ((buffer != null));
            return content;
        } catch (IOException exc) {
            System.out.println("Error when trying to access the file");
            return content;
        }
    }
    synchronized void writeContent(int n) {
        try (FileWriter file = new FileWriter(pathFile)) {
            String content = String.valueOf(n);
            file.write(content);
        } catch (IOException exc) {
            System.out.println("Error when trying to access the file");
        }
    }
}