package com.intervale.test.library.rest;

import com.intervale.test.library.dto.request.CreateBookRequestDto;
import com.intervale.test.library.dto.response.CreateBookResponseDto;
import com.intervale.test.library.model.Book;
import com.intervale.test.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("books")
    public ResponseEntity<CreateBookResponseDto> createBook(@RequestBody CreateBookRequestDto requestDto) {
        try {
            final CreateBookResponseDto responseDto = bookService.save(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        }
    }
}
