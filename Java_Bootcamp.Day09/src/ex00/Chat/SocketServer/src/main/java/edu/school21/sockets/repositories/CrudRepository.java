package edu.school21.sockets.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void create(T o);
    Optional<T> findById(Long id);
    List<T> findAll();
    void update(T o);
    void delete(Long id);
}
