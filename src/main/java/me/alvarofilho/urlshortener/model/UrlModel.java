package me.alvarofilho.urlshortener.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "Url")
public class UrlModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull(message = "Url Original may not be null")
    private String url;

    private String shortUrl;

    private int clicks = 0;

    private LocalDate date;
}
