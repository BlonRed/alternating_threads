package ru.blonred.testtask.alternatingThreads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

class WriteAndRead {
    private String state;
    private final String pathFile;

    public WriteAndRead() {
        pathFile = (getPathToResources() + "out.txt");
        writeContent(0);
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


    synchronized void threadOne(boolean running) {
        if (!running) {
            state = "Thread #2";
            notify();
            return;
        }
        int currentValue = Integer.parseInt(readContent());
        int newValue = currentValue + 1;
        writeContent(newValue);
        System.out.println("Old value: " + currentValue + "\n" + "New value: " + newValue + "\n" + "Thread #1" + "\n");

        state = "Thread #2";

        notify();
        try {
            while (!state.equals("Thread #1"))
                wait();
        } catch (InterruptedException exc) {
            System.out.println("Thread interruption");
        }
    }

    synchronized void threadTwo(boolean running) {
        if (!running) {
            state = "Thread #1";
            notify();
            return;
        }
        int currentValue = Integer.parseInt(readContent());
        int newValue = currentValue + 1;
        writeContent(newValue);
        System.out.println("Old value: " + currentValue + "\n" + "New value: " + newValue + "\n" + "Thread #2" + "\n");

        state = "Thread #1";

        notify();
        try {
            while (!state.equals("Thread #2"))
                wait();
        } catch (InterruptedException exc) {
            System.out.println("Thread interruption");
        }
    }
}