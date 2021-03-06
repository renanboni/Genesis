package model;

import java.util.*;
import java.util.function.Consumer;

public class EntityList <T extends Entity> implements Iterable<T> {

    protected final Map<Hash, T> allEntities;
    protected final Set<T> newEntities;
    protected final Set<T> removedEntities;
    protected final Set<T> updatedEntities;

    public EntityList() {
        allEntities = new HashMap<>();
        newEntities = new HashSet<>();
        removedEntities = new HashSet<>();
        updatedEntities = new HashSet<>();
    }

    public boolean add(T entity) {
        /*if (allEntities.containsKey(entity.getID())) {
            return false;
        }

        allEntities.put(entity.getID(), entity);
        newEntities.add(entity);*/

        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }
}
