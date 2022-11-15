package com.intervale.test.library.repository;

import com.intervale.test.library.model.Newspaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Long> {

}
