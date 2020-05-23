package me.alvarofilho.urlshortener.controller;

import lombok.SneakyThrows;
import me.alvarofilho.urlshortener.model.UrlModel;
import me.alvarofilho.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.Random;

@RestController
public class UrlResource {

    @Autowired
    private UrlRepository urlRepository;

    @SneakyThrows
    @GetMapping("/{hash}")
    public ResponseEntity<?> getUrlRedirect(@PathVariable(value = "hash") String hash) {
        var urlModel = urlRepository.findByShortUrl(hash);
        if (urlModel != null) {
            var httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(new URI(urlModel.getUrl()));
            return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"menssagem\": \"There is no such short url\"}");
    }

    @GetMapping("/url/{hash}")
    public ResponseEntity<?> getUrl(@PathVariable(value = "hash") String hash) {
        var urlModel = urlRepository.findByShortUrl(hash);
        if (urlModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"menssagem\": \"There is no such short url\"}");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(urlModel);
    }

    @PostMapping("/")
    public ResponseEntity<?> createUrl(@RequestBody @Valid UrlModel urlModel) {
        var existing = urlRepository.findByShortUrl(urlModel.getShortUrl());
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"menssagem\": \"There already exists a short url\"}");
        }
        if (urlModel.getShortUrl() == null) {
            urlModel.setShortUrl(createRandomUrl());
        }
        urlModel.setDate(LocalDate.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(urlRepository.save(urlModel));
    }

    private String createRandomUrl() {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
