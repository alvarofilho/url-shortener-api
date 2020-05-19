package me.alvarofilho.urlshortener.model;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "URL")
@Getter
public class UrlModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String urlOriginal;

    private String urlRandom;

    private BigInteger click;
}
