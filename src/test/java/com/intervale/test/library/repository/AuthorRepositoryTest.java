package com.intervale.test.library.repository;

import com.intervale.test.library.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    private final Author author = new Author("test", "test");

    @BeforeEach
    void setUp() {
        authorRepository.save(author);
    }

    @Test
    void testCreateReadDelete() {

        final List<Author> all = authorRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getFirstName()).isEqualTo("test");

        authorRepository.deleteAll();
        assertThat(authorRepository.findAll()).isEmpty();
    }

    @Test
    void whenFindByFirstNameAndLastName_thenReturnRightAuthor() {
        final Optional<Author> optionalAuthor = authorRepository.findByFirstNameAndLastName("test", "test");
        assertThat(optionalAuthor).isPresent();
        assertThat(optionalAuthor.get().getFirstName()).isEqualTo("test");
    }
}