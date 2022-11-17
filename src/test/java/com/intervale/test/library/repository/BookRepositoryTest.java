package com.intervale.test.library.repository;

import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
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
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    private final Book book = new Book("test", "test", LocalDate.EPOCH);

    @BeforeEach
    void setUp() {
        book.setId(1L);
        bookRepository.save(book);
    }

    @Test
    void testCreateReadDelete() {

        final List<Book> all = bookRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getTitle()).isEqualTo("test");

        bookRepository.deleteAll();
        assertThat(bookRepository.findAll()).isEmpty();
    }

    @Test
    void whenFindByDateOfPublication_thenReturnRightBooks() {
        final List<Book> books = bookRepository.findByDateOfPublication(book.getDateOfPublication());
        assertThat(books.get(0).getTitle()).isEqualTo("test");
    }

    @Test
    void whenFindByTitle_thenReturnRightBooks() {
        final List<Book> books = bookRepository.findByTitle(book.getTitle());
        assertThat(books.get(0).getTitle()).isEqualTo("test");
    }

    @Test
    void whenFindByDescription_thenReturnRightBooks() {
        final List<Book> books = bookRepository.findByDescription(book.getDescription());
        assertThat(books.get(0).getTitle()).isEqualTo("test");
    }

    @Test
    void whenFindAllByAuthorsIn_thenReturnRightBooks() {
        final Author author = new Author("testA", "testA");
        book.setAuthors(List.of(author));

        authorRepository.save(author);
        bookRepository.save(book);

        final List<Book> books = bookRepository.findAllByAuthorsIn(book.getAuthors());
        assertThat(books.get(0).getTitle()).isEqualTo("test");
    }
}