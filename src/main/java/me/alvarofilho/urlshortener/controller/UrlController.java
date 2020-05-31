package me.alvarofilho.urlshortener.controller;

import me.alvarofilho.urlshortener.erro.BadRequestException;
import me.alvarofilho.urlshortener.erro.ResourceNotFoundException;
import me.alvarofilho.urlshortener.model.UrlModel;
import me.alvarofilho.urlshortener.repository.UrlRepository;
import me.alvarofilho.urlshortener.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
public class UrlController {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlUtils urlUtils;

    @GetMapping("/{hash}")
    public ResponseEntity<?> getUrlRedirect(@PathVariable(value = "hash") String hash) {
        var urlModel = urlRepository.findByShortUrl(hash);
        if (urlModel == null) {
            throw new ResourceNotFoundException("There is no such short url");
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlModel.getUrl())).build();
    }

    @GetMapping("/url/{hash}")
    public ResponseEntity<?> getUrl(@PathVariable(value = "hash") String hash) {
        var urlModel = urlRepository.findByShortUrl(hash);
        if (urlModel == null) {
            throw new ResourceNotFoundException("There is no such short url");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(urlModel);
    }

    @PostMapping("/")
    public ResponseEntity<?> createUrl(@RequestBody @Valid UrlModel urlModel) {
        if (urlUtils.pingURL(urlModel.getUrl())) {
            if (urlModel.getShortUrl() == null) {
                urlModel.setShortUrl(urlUtils.createRandomUrl());
            }
            var existing = urlRepository.findByShortUrl(urlModel.getShortUrl());
            if (existing != null) {
                throw new BadRequestException("There already exists a short url");
            }
            urlModel.setDate(LocalDate.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(urlRepository.save(urlModel));
        }
        throw new BadRequestException("This URL is invalid");
    }

}
