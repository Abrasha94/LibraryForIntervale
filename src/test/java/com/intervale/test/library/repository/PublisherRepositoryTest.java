package com.intervale.test.library.repository;

import com.intervale.test.library.model.Publisher;
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
class PublisherRepositoryTest {

    @Autowired
    PublisherRepository publisherRepository;

    private final Publisher publisher = new Publisher("test");

    @BeforeEach
    void setUp() {
        publisherRepository.save(publisher);
    }

    @Test
    void testCreateReadDelete() {

        final List<Publisher> all = publisherRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getNameOf()).isEqualTo("test");

        publisherRepository.deleteAll();
        assertThat(publisherRepository.findAll()).isEmpty();
    }

    @Test
    void whenFindByNameOf_thenReturnRightPublisher() {
        final Optional<Publisher> optionalPublisher = publisherRepository.findByNameOf("test");
        assertThat(optionalPublisher).isPresent();
        assertThat(optionalPublisher.get().getNameOf()).isEqualTo("test");
    }
}