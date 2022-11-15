package com.intervale.test.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BaseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "dateOfPublication")
    private Date dateOfPublication;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public BaseProduct(String title, String description, Date dateOfPublication) {
        this.title = title;
        this.description = description;
        this.dateOfPublication = dateOfPublication;
    }
}
