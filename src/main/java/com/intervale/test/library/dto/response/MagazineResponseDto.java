package com.intervale.test.library.dto.response;

import com.intervale.test.library.model.Magazine;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MagazineResponseDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate dateOfPublication;
    private String publisher;

    public static MagazineResponseDto fromMagazine(Magazine magazine) {
        final MagazineResponseDto responseDto = new MagazineResponseDto();
        responseDto.setId(magazine.getId());
        responseDto.setTitle(magazine.getTitle());
        responseDto.setDescription(magazine.getDescription());
        responseDto.setDateOfPublication(magazine.getDateOfPublication());
        responseDto.setPublisher(magazine.getPublisher().toString());
        return responseDto;
    }
}
