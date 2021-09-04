package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class RolColSumTest {

    @Test
    public void sum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] sums = RolColSum.sum(matrix);
        assertEquals(6, sums[0].getRowSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(24, sums[2].getRowSum());
        assertEquals(12, sums[0].getColSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(18, sums[2].getColSum());
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] sums = RolColSum.asyncSum(matrix);
        assertEquals(6, sums[0].getRowSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(24, sums[2].getRowSum());
        assertEquals(12, sums[0].getColSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(18, sums[2].getColSum());
    }
}