package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, Id extends Long> {
    protected Map<Long, T> map = new HashMap<>();

    Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    T findById(Id id){
        return map.get(id);
    }

    T save(T object){
        if (object != null) {
            // If object has no Id yet, we create a new Id with getNextId()
            if (object.getId() == null) {
                object.setId(getNextId());
            }
            map.put(object.getId(), object); // saves object in map with object's key
        }
        else {
            throw new RuntimeException("Object can't be null.");
        }
        return object;
    }

    void deleteById(Id id){
        map.remove(id);
    }

    void delete(T object){
        // Use lambda expression
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }

    private Long getNextId() {
        Long nextId = null; // To avoid null pointer exception

        // If there is already an element in "map", nextId takes the size of the map + 1
        try {
            nextId = Collections.max(map.keySet()) + 1;
        }
        // If there is nothing in "map", nextId = 1
        catch (NoSuchElementException e) {
            nextId = 1L;
        }

        return nextId;
    }
}
