package me.alvarofilho.urlshortener.erro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

    private Date timestamp;
    private String error;
    private int status;
    private String message;
    private String details;

}
