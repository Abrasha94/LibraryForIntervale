package com.intervale.test.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Newspaper")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Newspaper extends BaseProduct {

    public Newspaper(String title, String description, LocalDate dateOfPublication) {
        super(title, description, dateOfPublication);
    }

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @ToString.Exclude
    private Publisher publisher;
}
