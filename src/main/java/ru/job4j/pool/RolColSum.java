package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> futureSums = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futureSums.put(i, getCompletableFuture(matrix, i));
        }
        Sums[] sums = new Sums[matrix.length];
        for (Map.Entry<Integer, CompletableFuture<Sums>> entry : futureSums.entrySet()) {
            sums[entry.getKey()] = entry.getValue().get();
        }

        return sums;
    }

    private static CompletableFuture<Sums> getCompletableFuture(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int columnSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                columnSum += matrix[j][i];
            }
            return new Sums(rowSum, columnSum);
        });
    }

    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

}