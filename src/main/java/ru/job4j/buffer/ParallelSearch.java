package ru.job4j.buffer;

import ru.job4j.wait.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        );
        producer.start();
        producer.join();
        while (queue.size() > 0) {
            Thread.sleep(500);
        }
        consumer.interrupt();
    }
}