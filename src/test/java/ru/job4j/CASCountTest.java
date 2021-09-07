package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CASCountTest {
    @Test
    public void test() throws InterruptedException {
        CASCount count = new CASCount();
        Thread thread1 = new Thread(new MyRunnable(count));
        Thread thread2 = new Thread(new MyRunnable(count));
        Thread thread3 = new Thread(new MyRunnable(count));
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        assertEquals(30_000, count.get());
    }

    static class MyRunnable implements Runnable {
        private CASCount count;

        public MyRunnable(CASCount count) {
            this.count = count;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10_000; j++) {
                count.increment();
                Thread.yield();
            }
        }
    }
}