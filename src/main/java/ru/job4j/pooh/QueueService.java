package ru.job4j.pooh;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String name = req.name();
        queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
        String text = req.text();
        int status = 200;
        switch (req.method()) {
            case "POST" -> {
                queue.get(name).add(text);
                status = 201;
            }
            case "GET" -> text = queue.get(name).poll();
        }
        if (Objects.isNull(text)) {
            text = "";
            status = 204;
        }
        return new Resp(text, status);
    }
}