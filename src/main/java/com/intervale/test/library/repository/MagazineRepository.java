package com.intervale.test.library.repository;

import com.intervale.test.library.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository  extends JpaRepository<Magazine, Long> {

}
