package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.BookRequestDto;
import com.intervale.test.library.dto.response.BookResponseDto;
import com.intervale.test.library.exception.BookNotFoundException;
import com.intervale.test.library.model.Book;
import com.intervale.test.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books/")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@RequestBody BookRequestDto requestDto) {
        try {
            final Book book = bookService.save(requestDto);
            return new ResponseEntity<>(BookResponseDto.fromBook(book), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Book Not Created", e);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<BookResponseDto> updateBookDescription(@PathVariable("id") Long id,
                                                                 @RequestBody String description) {
        try {
            final Book book = bookService.updateDescription(id, description);
            return new ResponseEntity<>(BookResponseDto.fromBook(book), HttpStatus.OK);
        } catch (BookNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Book Not Updated", e);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") Long id) {
        try {
            final Book book = bookService.findById(id);
            return new ResponseEntity<>(BookResponseDto.fromBook(book), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Book Not Found", e);
        }
    }

    @GetMapping("date")
    public ResponseEntity<List<BookResponseDto>> getBookByDateOfPublication(
            @RequestParam(value = "dateOfPublication") String dateOfPublication) {
        try {
            final List<Book> books = bookService.findByDateOfPublication(dateOfPublication);
            if (books == null) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Book Not Found");
            }
            return new ResponseEntity<>(books.stream().map(BookResponseDto::fromBook).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<BookResponseDto>> getBookByTitle(@PathVariable("title") String title) {
        try {
            final List<Book> books = bookService.findByTitle(title);
            if (books == null) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Book Not Found");
            }
            return new ResponseEntity<>(books.stream().map(BookResponseDto::fromBook).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("desc/{description}")
    public ResponseEntity<List<BookResponseDto>> getBookByDescription(@PathVariable("description") String description) {
        try {
            final List<Book> books = bookService.findByDescription(description);
            if (books == null) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Book Not Found");
            }
            return new ResponseEntity<>(books.stream().map(BookResponseDto::fromBook).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @GetMapping("/author/{lastname}/{firstname}")
    public ResponseEntity<List<BookResponseDto>> getBookByAuthor(@PathVariable("lastname") String lastName,
                                                                 @PathVariable("firstname") String firstName) {
        try {
            final List<Book> books = bookService.findByAuthor(firstName, lastName);
            if (books == null) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Book or Author Not Found");
            }
            return new ResponseEntity<>(books.stream().map(BookResponseDto::fromBook).collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error On Server", e);
        }
    }
}
