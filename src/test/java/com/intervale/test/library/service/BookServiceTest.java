package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.BookRequestDto;
import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
import com.intervale.test.library.repository.AuthorRepository;
import com.intervale.test.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    private final Book book = new Book("title", "description", LocalDate.EPOCH);
    private final Author author = new Author("testFirstName", "testLastName");
    private final BookRequestDto requestDto = new BookRequestDto("title", "description",
            LocalDate.EPOCH, List.of("test test"));

    @Test
    void whenSaveBook_thenDtoEqualBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        final Book saveBook = bookService.save(requestDto);

        verify(bookRepository, times(1)).save(any(Book.class));
        assertThat(saveBook.getTitle()).isEqualTo("title");
        assertThat(saveBook.getDescription()).isEqualTo("description");
    }

    @Test
    void whenUpdate_thenEqualDescriptions() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        final Book updatedBook = bookService.updateDescription(1L, "Updated description");

        verify(bookRepository, times(1)).updateDescription("Updated description", 1L);
        assertThat(updatedBook.getDescription()).isEqualTo("Updated description");
    }

    @Test
    void whenFindById_thenReturnValidDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        final Book findedBook = bookService.findById(1L);

        verify(bookRepository, times(1)).findById(1L);
        assertThat(findedBook.getTitle()).isEqualTo("title");
    }

    @Test
    void whenFindByDateOfPublication_thenReturnNotEmptyList() {
        when(bookRepository.findByDateOfPublication(any(LocalDate.class))).thenReturn(List.of(book));

        final List<Book> books = bookService.findByDateOfPublication("2022-11-16");

        verify(bookRepository, times(1)).findByDateOfPublication(any(LocalDate.class));
        assertThat(books).isNotEmpty();
    }

    @Test
    void whenFindByTitle_thenReturnValidDto() {
        when(bookRepository.findByTitle("title")).thenReturn(List.of(book));

        final List<Book> books = bookService.findByTitle("title");

        verify(bookRepository, times(1)).findByTitle("title");
        assertThat(books.get(0).getTitle()).isEqualTo("title");
    }

    @Test
    void whenFindByDescription_thenReturnValidDto() {
        when(bookRepository.findByDescription("description")).thenReturn(List.of(book));

        final List<Book> books = bookService.findByDescription("description");

        verify(bookRepository, times(1)).findByDescription("description");
        assertThat(books.get(0).getDescription()).isEqualTo("description");
    }

    @Test
    void whenFindByAuthor_thenReturnValidDto() {
        when(authorRepository.findByFirstNameAndLastName("test", "test")).thenReturn(Optional.of(author));
        when(bookRepository.findAllByAuthorsIn(List.of(author))).thenReturn(List.of(book));


        final List<Book> books = bookService.findByAuthor("test", "test");

        verify(bookRepository, times(1)).findAllByAuthorsIn(List.of(author));
        assertThat(books.get(0).getTitle()).isEqualTo("title");
    }

    @Test
    void whenDeleteById_thenBookDeleted() {
        bookService.deleteById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}