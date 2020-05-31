package me.alvarofilho.urlshortener.erro;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }

}
