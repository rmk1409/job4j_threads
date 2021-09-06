package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CASCountTest {
    @Test
    public void test() throws InterruptedException {
        CASCount count = new CASCount();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10_000; j++) {
                    count.increment();
                    Thread.yield();
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assertEquals(100_000, count.get());
    }
}