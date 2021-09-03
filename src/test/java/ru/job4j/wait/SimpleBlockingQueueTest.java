package ru.job4j.wait;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {
    @Test
    public void testConsumerWaiting() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(15);
        Thread consumer = new Thread(new Consumer(queue));
        consumer.start();
        Thread.sleep(500);
        assertEquals(Thread.State.WAITING, consumer.getState());
        consumer.interrupt();
    }

    @Test
    public void testProducerWaiting() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(new Producer(queue));
        producer.start();
        Thread.sleep(500);
        assertEquals(Thread.State.WAITING, producer.getState());
        producer.interrupt();
    }

    @Test
    public void testTerminatedState() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(15);
        Thread consumer = new Thread(new Consumer(queue));
        Thread producer = new Thread(new Producer(queue));
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
        assertEquals(Thread.State.TERMINATED, consumer.getState());
        assertEquals(Thread.State.TERMINATED, consumer.getState());
    }

    static class Consumer implements Runnable {
        private SimpleBlockingQueue<Integer> queue;

        public Consumer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }

    static class Producer implements Runnable {
        private SimpleBlockingQueue<Integer> queue;

        public Producer(SimpleBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        }
    }
}