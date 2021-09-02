package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Writer {
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