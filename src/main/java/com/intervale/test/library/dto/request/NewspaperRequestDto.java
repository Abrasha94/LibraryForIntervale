package com.intervale.test.library.dto.request;

import com.intervale.test.library.model.Newspaper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
