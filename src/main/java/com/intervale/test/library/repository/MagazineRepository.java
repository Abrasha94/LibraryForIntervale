package com.intervale.test.library.repository;

import com.intervale.test.library.model.Magazine;
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
public interface MagazineRepository extends JpaRepository<Magazine, Long> {

    List<Magazine> findByDateOfPublication(LocalDate dateOfPublication);

    List<Magazine> findByTitle(String title);

    List<Magazine> findByDescription(String description);

    @Transactional
    @Modifying
    @Query("update Magazine m set m.description = ?1 where m.id = ?2")
    void updateDescription(String description, Long id);

}
