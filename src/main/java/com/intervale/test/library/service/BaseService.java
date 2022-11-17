package com.intervale.test.library.service;

import java.util.List;

public interface BaseService<T, K> {

    T save(K savedObject);

    T updateDescription(Long id, String description);

    T findById(Long id);

    List<T> findByDateOfPublication(String dateOfPublication);

    List<T> findByTitle(String title);

    List<T> findByDescription(String description);

    void deleteById(Long id);
}
