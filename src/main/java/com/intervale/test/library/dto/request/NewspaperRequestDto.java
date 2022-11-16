package com.intervale.test.library.dto.request;

import com.intervale.test.library.model.Newspaper;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NewspaperRequestDto {

    private String title;
    private String description;
    private LocalDate dateOfPublication;
    private String publisher;

    public Newspaper toNewspaperWithoutPublisher() {
        final Newspaper newspaper = new Newspaper();
        newspaper.setTitle(title);
        newspaper.setDescription(description);
        newspaper.setDateOfPublication(dateOfPublication);
        return newspaper;
    }
}
