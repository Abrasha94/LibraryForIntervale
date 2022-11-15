package com.intervale.test.library.dto.request;

import com.intervale.test.library.model.Book;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
public class CreateBookRequestDto implements BaseRequestDto{

    private String title;
    private String description;
    private Date dateOfPublication;
    private Map<String, String> authors;

    public Book toBook() {
        final Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setDateOfPublication(dateOfPublication);
        return book;
    }
}
