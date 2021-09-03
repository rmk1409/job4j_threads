package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer previous;
        do {
            previous = count.get();
        } while (!count.compareAndSet(previous, previous + 1));
    }

    public int get() {
        return count.get();
    }
}