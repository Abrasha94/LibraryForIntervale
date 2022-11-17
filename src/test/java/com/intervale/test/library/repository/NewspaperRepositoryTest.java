package com.intervale.test.library.repository;

import com.intervale.test.library.model.Newspaper;
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
public class NewspaperRepositoryTest {

    @Autowired
    NewspaperRepository newspaperRepository;

    private final Newspaper newspaper = new Newspaper("test", "test", LocalDate.EPOCH);

    @BeforeEach
    void setUp() {
        newspaperRepository.save(newspaper);
    }

    @Test
    void testCreateReadDelete() {

        final List<Newspaper> all = newspaperRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getTitle()).isEqualTo("test");

        newspaperRepository.deleteAll();
        assertThat(newspaperRepository.findAll()).isEmpty();
    }

    @Test
    void whenFindByDateOfPublication_thenReturnRightNewspapers() {
        final List<Newspaper> newspapers = newspaperRepository.findByDateOfPublication(newspaper.getDateOfPublication());
        assertThat(newspapers.get(0).getTitle()).isEqualTo("test");
    }

    @Test
    void whenFindByTitle_thenReturnRightNewspapers() {
        final List<Newspaper> newspapers = newspaperRepository.findByTitle(newspaper.getTitle());
        assertThat(newspapers.get(0).getTitle()).isEqualTo("test");
    }

    @Test
    void whenFindByDescription_thenReturnRightNewspapers() {
        final List<Newspaper> newspapers = newspaperRepository.findByDescription(newspaper.getDescription());
        assertThat(newspapers.get(0).getTitle()).isEqualTo("test");
    }
}
