package com.intervale.test.library.dto.response;

import com.intervale.test.library.model.Newspaper;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class NewspaperResponseDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate dateOfPublication;
    private String publisher;

    public static NewspaperResponseDto fromNewspaper(Newspaper newspaper) {
        final NewspaperResponseDto responseDto = new NewspaperResponseDto();
        responseDto.setId(newspaper.getId());
        responseDto.setTitle(newspaper.getTitle());
        responseDto.setDescription(newspaper.getDescription());
        responseDto.setDateOfPublication(newspaper.getDateOfPublication());
        responseDto.setPublisher(newspaper.getPublisher().toString());
        return responseDto;
    }
}
