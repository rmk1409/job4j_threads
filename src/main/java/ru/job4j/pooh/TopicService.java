package ru.job4j.pooh;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    Map<String, ConcurrentLinkedQueue<String>> msgs = new ConcurrentHashMap<>();
    Map<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> clients = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String name = req.name();
        msgs.putIfAbsent(name, new ConcurrentLinkedQueue<>());
        String text = req.text();
        int status = 200;
        switch (req.method()) {
            case "POST" -> {
                msgs.get(name).add(text);
                status = 201;
                String finalText = text;
                clients.forEach((id, clientMsgs) -> {
                    clientMsgs.putIfAbsent(name, new ConcurrentLinkedQueue<>());
                    clientMsgs.get(name).add(finalText);
                });
            }
            case "GET" -> {
                String id = req.getId();
                clients.computeIfAbsent(id, key -> {
                    ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> clientMsgs = new ConcurrentHashMap<>();
                    msgs.forEach((k, v) -> clientMsgs.put(k, new ConcurrentLinkedQueue<>(v)));
                    return clientMsgs;
                });
                text = clients.get(id).get(name).poll();
            }
        }
        if (Objects.isNull(text)) {
            text = "";
            status = 204;
        }
        return new Resp(text, status);
    }
}