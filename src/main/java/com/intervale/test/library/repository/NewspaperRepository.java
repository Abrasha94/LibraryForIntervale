package com.intervale.test.library.repository;

import com.intervale.test.library.model.Magazine;
import com.intervale.test.library.model.Newspaper;
import com.intervale.test.library.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Long> {

    List<Newspaper> findByDateOfPublication(Date dateOfPublication);

    List<Newspaper> findByTitle(String title);

    List<Newspaper> findByDescription(String description);

    Optional<Newspaper> findByPublisher(Publisher publisher);

    @Transactional
    @Modifying
    @Query("update Book b set b.description = ?1 where b.id = ?2")
    void updateDescription(String description, Long id);
}
