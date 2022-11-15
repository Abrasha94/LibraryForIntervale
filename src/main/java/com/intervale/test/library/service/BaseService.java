package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.BaseRequestDto;
import com.intervale.test.library.dto.response.BaseResponseDto;
import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;

import java.util.Date;
import java.util.List;

public interface BaseService<T> {

    BaseResponseDto save(BaseRequestDto saveObject);

    T updateDescription(Long id, String description);

    T findById(Long id);

    List<T> findByDateOfPublication(Date dateOfPublication);

    List<T> findByTitle(String title);

    List<T> findByDescription(String description);

    T deleteById(Long id);
}
