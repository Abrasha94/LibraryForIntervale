package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.BookRequestDto;
import com.intervale.test.library.dto.response.BookResponseDto;
import com.intervale.test.library.exception.BookNotFoundException;
import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
import com.intervale.test.library.repository.AuthorRepository;
import com.intervale.test.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements BaseService<BookResponseDto, BookRequestDto> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookResponseDto save(BookRequestDto requestDto) {
        final Book book = requestDto.toBookWithoutAuthors();

        //Save Authors in DB if they do not exist
        saveAuthorIfNotExist(requestDto, book);

        final Book saveBook = bookRepository.save(book);

        return BookResponseDto.fromBook(saveBook);
    }

    @Override
    public BookResponseDto updateDescription(Long id, String description) {
        final Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Can't find the book by id: " + id));
        bookRepository.updateDescription(description, id);
        book.setDescription(description);
        return BookResponseDto.fromBook(book);
    }

    @Override
    @Cacheable("books")
    public BookResponseDto findById(Long id) {
        final Book book = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Can't find the book by id: " + id));
        return BookResponseDto.fromBook(book);
    }

    @Override
    public List<BookResponseDto> findByDateOfPublication(String dateOfPublication) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        final LocalDate date = LocalDate.parse(dateOfPublication, formatter);
        final List<Book> books = bookRepository.findByDateOfPublication(date);
        if (books.isEmpty()) {
            return null;
        }
        return books.stream().map(BookResponseDto::fromBook).collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDto> findByTitle(String title) {
        final List<Book> books = bookRepository.findByTitle(title);
        if (books.isEmpty()) {
            return null;
        }
        return books.stream().map(BookResponseDto::fromBook).collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDto> findByDescription(String description) {
        final List<Book> books = bookRepository.findByDescription(description);
        if (books.isEmpty()) {
            return null;
        }
        return books.stream().map(BookResponseDto::fromBook).collect(Collectors.toList());
    }

    public List<BookResponseDto> findByAuthor(String firstName, String lastName) {
        final Optional<Author> author = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (author.isEmpty()) {
            return null;
        }
        final ArrayList<Author> authors = new ArrayList<>();
        authors.add(author.get());
        final List<Book> books = bookRepository.findAllByAuthorsIn(authors);
        if (books.isEmpty()) {
            return null;
        }
        return books.stream().map(BookResponseDto::fromBook).collect(Collectors.toList());
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
