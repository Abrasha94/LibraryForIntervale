package com.intervale.test.library.repository;

import com.intervale.test.library.model.Magazine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
class MagazineRepositoryTest {

    @Autowired
    MagazineRepository magazineRepository;

    private final Magazine magazine = new Magazine("test", "test", LocalDate.EPOCH);

    @BeforeEach
    void setUp() {
        magazineRepository.save(magazine);
    }

    @Test
    void testCreateReadDelete() {

        final List<Magazine> all = magazineRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getTitle()).isEqualTo("test");

        magazineRepository.deleteAll();
        assertThat(magazineRepository.findAll()).isEmpty();
    }

    @Test
    void whenFindByDateOfPublication_thenReturnRightMagazines() {
        final List<Magazine> magazines = magazineRepository.findByDateOfPublication(magazine.getDateOfPublication());
        assertThat(magazines.get(0).getTitle()).isEqualTo("test");
    }

    @Test
    void whenFindByTitle_thenReturnRightMagazines() {
        final List<Magazine> magazines = magazineRepository.findByTitle(magazine.getTitle());
        assertThat(magazines.get(0).getTitle()).isEqualTo("test");
    }

    @Test
    void whenFindByDescription_thenReturnRightMagazines() {
        final List<Magazine> magazines = magazineRepository.findByDescription(magazine.getDescription());
        assertThat(magazines.get(0).getTitle()).isEqualTo("test");
    }
}