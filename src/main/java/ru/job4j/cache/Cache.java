package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        int modelId = model.getId();
        return Objects.nonNull(memory.computeIfPresent(modelId, (id, oldModel) -> {
            Base cachedModel = memory.get(modelId);
            if (model.getVersion() != cachedModel.getVersion()) {
                throw new OptimisticException(String.format("Different ids: %d and %d", modelId, cachedModel.getVersion()));
            }
            Base newBase = new Base(id, oldModel.getVersion() + 1);
            newBase.setName(model.getName());
            return newBase;
        }));
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}