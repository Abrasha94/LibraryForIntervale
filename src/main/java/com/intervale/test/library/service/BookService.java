package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.BookRequestDto;
import com.intervale.test.library.exception.BookNotFoundException;
import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
import com.intervale.test.library.repository.AuthorRepository;
import com.intervale.test.library.repository.BookRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class BookService implements BaseService<Book, BookRequestDto> {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book save(BookRequestDto requestDto) {
        final Book book = requestDto.toBookWithoutAuthors();

        //Save Authors in DB if they do not exist
        saveAuthorIfNotExist(requestDto, book);

        return bookRepository.save(book);
    }

    @Override
    public Book updateDescription(Long id, String description) {
        final Book book = findById(id);
        bookRepository.updateDescription(description, id);
        book.setDescription(description);
        return book;
    }

    @Override
    @Cacheable("books")
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Can't find the book by id: " + id));
    }

    @Override
    public List<Book> findByDateOfPublication(String dateOfPublication) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        final LocalDate date = LocalDate.parse(dateOfPublication, formatter);
        final List<Book> books = bookRepository.findByDateOfPublication(date);
        if (books.isEmpty()) {
            return null;
        }
        return books;
    }

    @Override
    public List<Book> findByTitle(String title) {
        final List<Book> books = bookRepository.findByTitle(title);
        if (books.isEmpty()) {
            return null;
        }
        return books;
    }

    @Override
    public List<Book> findByDescription(String description) {
        final List<Book> books = bookRepository.findByDescription(description);
        if (books.isEmpty()) {
            return null;
        }
        return books;
    }

    public List<Book> findByAuthor(String firstName, String lastName) {
        final Optional<Author> author = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (author.isEmpty()) {
            return null;
        }
        final List<Author> authors = new ArrayList<>();
        authors.add(author.get());
        final List<Book> books = bookRepository.findAllByAuthorsIn(authors);
        if (books.isEmpty()) {
            return null;
        }
        return books;
    }

    @Override
    @CacheEvict("books")
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private void saveAuthorIfNotExist(BookRequestDto requestDto, Book book) {
        for (String authorString : requestDto.getAuthors()) {
            final String[] authorFirstAndLastNames = authorString.split(" ");

            final Optional<Author> optionalAuthor = authorRepository
                    .findByFirstNameAndLastName(authorFirstAndLastNames[0], authorFirstAndLastNames[1]);

            if (optionalAuthor.isEmpty()) {
                final Author author = new Author();
                author.setFirstName(authorFirstAndLastNames[0]);
                author.setLastName(authorFirstAndLastNames[1]);
                final Author saveAuthor = authorRepository.save(author);
                book.getAuthors().add(saveAuthor);
            } else {
                book.getAuthors().add(optionalAuthor.get());
            }
        }
    }
}
