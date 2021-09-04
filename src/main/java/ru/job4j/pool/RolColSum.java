package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Sums();
        }
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            result[i].rowSum = rowSum;
            result[i].colSum = colSum;
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Sums();
        }

        Map<Integer, CompletableFuture<Integer>> futureRowSums = new HashMap<>();
        Map<Integer, CompletableFuture<Integer>> futureColumnSums = new HashMap<>();

        for (int i = 0; i < matrix.length; i++) {
            futureRowSums.put(i, getRowCompletableFuture(matrix, i));
            futureColumnSums.put(i, getColumnCompletableFuture(matrix, i));
        }

        for (Map.Entry<Integer, CompletableFuture<Integer>> entry : futureRowSums.entrySet()) {
            result[entry.getKey()].rowSum = entry.getValue().get();
        }
        for (Map.Entry<Integer, CompletableFuture<Integer>> entry : futureColumnSums.entrySet()) {
            result[entry.getKey()].colSum = entry.getValue().get();
        }

        return result;
    }

    private static CompletableFuture<Integer> getRowCompletableFuture(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                sum += matrix[i][j];
            }
            return sum;
        });
    }

    private static CompletableFuture<Integer> getColumnCompletableFuture(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int[] ints : matrix) {
                sum += ints[i];
            }
            return sum;
        });
    }

    public static class Sums {
        private int rowSum;
        private int colSum;
        /* Getter and Setter */

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

}