package ru.job4j.pool;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;

public class ParallelSearchTest {
    private ForkJoinPool pool;

    @Before
    public void setUp() {
        pool = new ForkJoinPool();
    }

    @Test
    public void found() {
        Integer[] array = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120};
        ParallelSearch<Integer> searcher = new ParallelSearch<>(array, 10);
        assertEquals(0, (int) pool.invoke(searcher));
    }

    @Test
    public void notFound() {
        Integer[] array = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120};
        ParallelSearch<Integer> searcher = new ParallelSearch<>(array, 140);
        assertEquals(-1, (int) pool.invoke(searcher));
    }
}