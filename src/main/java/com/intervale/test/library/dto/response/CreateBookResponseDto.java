package com.intervale.test.library.dto.response;

import com.intervale.test.library.model.Author;
import com.intervale.test.library.model.Book;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class CreateBookResponseDto implements BaseResponseDto {

    private Long id;
    private String title;
    private String description;
    private Date dateOfPublication;
    private List<String> authors;

    public static CreateBookResponseDto fromBook(Book book) {
        final CreateBookResponseDto responseDto = new CreateBookResponseDto();
        responseDto.setId(book.getId());
        responseDto.setTitle(book.getTitle());
        responseDto.setDescription(book.getDescription());
        responseDto.setDateOfPublication(book.getDateOfPublication());
        responseDto.setAuthors(book.getAuthors().stream()
                .map(Author::toString).collect(Collectors.toList()));
        return responseDto;
    }
}
