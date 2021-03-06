package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class Reader {
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