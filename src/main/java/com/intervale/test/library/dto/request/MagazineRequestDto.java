package com.intervale.test.library.dto.request;

import com.intervale.test.library.model.Magazine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MagazineRequestDto {

    private String title;
    private String description;
    private LocalDate dateOfPublication;
    private String publisher;

    public Magazine toMagazineWithoutPublisher() {
        final Magazine magazine = new Magazine();
        magazine.setTitle(title);
        magazine.setDescription(description);
        magazine.setDateOfPublication(dateOfPublication);
        return magazine;
    }
}
