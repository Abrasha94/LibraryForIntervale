package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.BookRequestDto;
import com.intervale.test.library.dto.response.BookResponseDto;
import com.intervale.test.library.exception.BookNotFoundException;
import com.intervale.test.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            final BookResponseDto responseDto = bookService.save(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<BookResponseDto> updateBookDescription(@PathVariable("id") Long id,
                                                                 @RequestBody String description) {
        try {
            final BookResponseDto responseDto = bookService.updateDescription(id, description);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("id") Long id) {
        try {
            final BookResponseDto responseDto = bookService.findById(id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("date")
    public ResponseEntity<List<BookResponseDto>> getBookByDateOfPublication(@RequestParam(value = "dateOfPublication") String dateOfPublication) {
        try {
            final List<BookResponseDto> responseDto = bookService.findByDateOfPublication(dateOfPublication);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("title/{title}")
    public ResponseEntity<List<BookResponseDto>> getBookByTitle(@PathVariable("title") String title) {
        try {
            final List<BookResponseDto> responseDto = bookService.findByTitle(title);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("desc/{description}")
    public ResponseEntity<List<BookResponseDto>> getBookByDescription(@PathVariable("description") String description) {
        try {
            final List<BookResponseDto> responseDto = bookService.findByDescription(description);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/author/{lastname}/{firstname}")
    public ResponseEntity<List<BookResponseDto>> getBookByAuthor(@PathVariable("lastname") String lastName,
                                                                 @PathVariable("firstname") String firstName) {
        try {
            final List<BookResponseDto> responseDto = bookService.findByAuthor(firstName, lastName);
            if (responseDto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Long id) {
        try {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
