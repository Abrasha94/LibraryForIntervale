package com.intervale.test.library.repository;

import com.intervale.test.library.model.Newspaper;
import com.intervale.test.library.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Long> {

    List<Newspaper> findByDateOfPublication(LocalDate dateOfPublication);

    List<Newspaper> findByTitle(String title);

    List<Newspaper> findByDescription(String description);

    @Transactional
    @Modifying
    @Query("update Newspaper n set n.description = ?1 where n.id = ?2")
    void updateDescription(String description, Long id);
}
