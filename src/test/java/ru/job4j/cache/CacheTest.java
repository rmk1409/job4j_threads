package ru.job4j.cache;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CacheTest {
    private Cache cache;

    @Before
    public void setUp() throws Exception {
        cache = new Cache();
    }

    @Test
    public void add() {
        Base model = new Base(1, 1);
        assertTrue(cache.add(model));
        assertFalse(cache.add(model));
    }

    @Test(expected = OptimisticException.class)
    public void updateWithException() {
        Base model = new Base(1, 1);
        cache.add(model);
        cache.update(new Base(model.getId(), model.getVersion() + 1));
    }

    @Test
    public void updateSuccess() {
        Base model = new Base(1, 1);
        cache.add(model);
        model.setName("test-name");
        assertTrue(cache.update(model));
    }
}