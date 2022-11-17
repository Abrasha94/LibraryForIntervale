package com.intervale.test.library.dto.request;

import com.intervale.test.library.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {

    private String title;
    private String description;
    private LocalDate dateOfPublication;
    private List<String> authors;

    public Book toBookWithoutAuthors() {
        final Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setDateOfPublication(dateOfPublication);
        return book;
    }
}
