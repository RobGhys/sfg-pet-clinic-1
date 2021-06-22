package guru.springframework.sfgpetclinic.services;

import java.util.Set;

public interface CrudService <T, Id>{
    Set<T> findAll();
    T findById(Id id);
    T save(T object);
    void delete(T object);
    void deleteById(Id id);
}
