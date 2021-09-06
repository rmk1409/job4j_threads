package ru.job4j.pooh;

import java.util.Objects;

public class Req {
    private final String method;
    private final String mode;
    private final String name;
    private final String text;
    private final String id;

    private Req(String method, String mode, String name, String text, String id) {
        this.method = method;
        this.mode = mode;
        this.name = name;
        this.text = text;
        this.id = id;
    }

    public static Req of(String content) {
        System.out.println(content);
        int firstSpace = content.indexOf(" ");
        int firstSlash = content.indexOf("/", firstSpace + 1);
        int secondSlash = content.indexOf("/", firstSlash + 1);
        String method = content.substring(0, firstSpace);
        String mode = content.substring(firstSlash + 1, secondSlash);
        boolean isModeTopic = Objects.equals(mode, "topic");
        boolean isMethodGet = Objects.equals(method, "GET");
        String name = content.substring(secondSlash + 1, content.indexOf(isModeTopic && isMethodGet ? "/" : " ", secondSlash + 1));
        String id = "";
        if (isModeTopic && isMethodGet) {
            int thirdSlash = content.indexOf("/", secondSlash + 1);
            id = content.substring(thirdSlash + 1, content.indexOf(" ", thirdSlash + 1));
        }
        String text = content.substring(content.indexOf("\r\n\r\n") + 4);
        return new Req(method, mode, name, text, id);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String text() {
        return text;
    }

    public String name() {
        return name;
    }

    public String getId() {
        return id;
    }
}