package com.intervale.test.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Publisher")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "publisher")
    @ToString.Exclude
    private List<Magazine> magazines;

    @OneToMany(mappedBy = "publisher")
    @ToString.Exclude
    private List<Newspaper> newspapers;

    public Publisher(String city) {
        this.city = city;
    }
}
