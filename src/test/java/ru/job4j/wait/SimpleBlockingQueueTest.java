package ru.job4j.wait;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.offer(i);
            }
        });
        consumer.start();
        Thread.sleep(500);
        assertEquals(Thread.State.WAITING, consumer.getState());
        producer.start();
        producer.join();
        consumer.join();
        assertEquals(Thread.State.TERMINATED, consumer.getState());
        assertEquals(Thread.State.TERMINATED, consumer.getState());
    }
}