package com.intervale.test.library.repository;

import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByDateOfPublication(Date dateOfPublication);

    List<Book> findByTitle(String title);

    List<Book> findByDescription(String description);

    List<Book> findByAuthors(List<Author> authors);

    @Transactional
    @Modifying
    @Query("update Book b set b.description = ?1 where b.id = ?2")
    void updateDescription(String description, Long id);
}
