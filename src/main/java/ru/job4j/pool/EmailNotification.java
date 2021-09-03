package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private static final String SUBJECT_TEMPLATE = "subject = Notification %s to email %s.";
    private static final String BODY_TEMPLATE = "body = Add a new event to %s";
    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    void emailTo(User user) {
        service.submit(() -> {
            String username = user.getUsername();
            String email = user.getEmail();
            String subject = String.format(SUBJECT_TEMPLATE, username, email);
            String body = String.format(BODY_TEMPLATE, username);
            send(subject, body, email);
        });
    }

    void close() throws InterruptedException {
        service.shutdown();
        while (!service.isTerminated()) {
            Thread.sleep(100);
        }
    }

    public void send(String subject, String body, String email) {

    }
}
