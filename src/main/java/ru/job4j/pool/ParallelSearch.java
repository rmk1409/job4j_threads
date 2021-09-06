package ru.job4j.pool;

import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T element;

    public ParallelSearch(T[] array, T element) {
        this(array, 0, array.length - 1, element);
    }

    private ParallelSearch(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, from, mid, element);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, mid + 1, to, element);
        leftSearch.fork();
        rightSearch.fork();
        Integer left = leftSearch.join();
        Integer right = rightSearch.join();
        return Math.max(left, right);
    }

    private Integer linearSearch() {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (element.equals(array[i])) {
                result = i;
                break;
            }
        }
        return result;
    }
}
