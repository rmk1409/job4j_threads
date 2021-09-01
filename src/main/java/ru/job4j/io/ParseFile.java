package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
}

class Writer {
    private final File file;

    public Writer(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }
}

class Reader {
    private final File file;

    public Reader(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return getContent(data -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(data -> data < 0x80);
    }

    private String getContent(Predicate<Integer> predicate) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) > 0) {
                if (predicate.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }
}
