package com.intervale.test.library.dto.response;

import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class BookResponseDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate dateOfPublication;
    private List<String> authors;

    public static BookResponseDto fromBook(Book book) {
        final BookResponseDto responseDto = new BookResponseDto();
        responseDto.setId(book.getId());
        responseDto.setTitle(book.getTitle());
        responseDto.setDescription(book.getDescription());
        responseDto.setDateOfPublication(book.getDateOfPublication());
        responseDto.setAuthors(book.getAuthors().stream()
                .map(Author::toString).collect(Collectors.toList()));
        return responseDto;
    }
}
