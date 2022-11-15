package com.intervale.test.library.service;

import com.intervale.test.library.dto.request.CreateBookRequestDto;
import com.intervale.test.library.dto.response.CreateBookResponseDto;
import com.intervale.test.library.exception.BookNotFoundException;
import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
import com.intervale.test.library.repository.AuthorRepository;
import com.intervale.test.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookService implements BaseService<Book> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public CreateBookResponseDto save(CreateBookRequestDto requestDto) {
        final Book book = bookRepository.save(requestDto.toBook());
        final Author author = new Author();
        author.setFirstName(requestDto.getAuthors().);
        authorRepository.save()
        return CreateBookResponseDto.fromBook(book);
    }

    @Override
    public Book updateDescription(Long id, String description) {
        bookRepository.updateDescription(description, id);
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Can't find the book by id: " + id));
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Can't find the book by id: " + id));
    }

    @Override
    public List<Book> findByDateOfPublication(Date dateOfPublication) {
        final List<Book> books = bookRepository.findByDateOfPublication(dateOfPublication);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Can't find the book by date publication: " + dateOfPublication.toString());
        }
        return books;
    }

    @Override
    public List<Book> findByTitle(String title) {
        final List<Book> books = bookRepository.findByTitle(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Can't find the book by title: " + title);
        }
        return books;
    }

    @Override
    public List<Book> findByDescription(String description) {
        final List<Book> books = bookRepository.findByDescription(description);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Can't find the book by description: " + description);
        }
        return books;
    }

    public List<Book> findByAuthor(Author author) {
        final ArrayList<Author> authors = new ArrayList<>();
        authors.add(author);
        final List<Book> books = bookRepository.findByAuthors(authors);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Can't find the book by author: " + author.toString());
        }
        return books;
    }

    @Override
    public Book deleteById(Long id) {
        final Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Can't find the book by id: " + id));
        bookRepository.deleteById(id);
        return book;
    }
}