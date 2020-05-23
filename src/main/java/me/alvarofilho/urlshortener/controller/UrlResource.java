package me.alvarofilho.urlshortener.controller;

import me.alvarofilho.urlshortener.model.UrlModel;
import me.alvarofilho.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UrlResource {

    @Autowired
    private UrlRepository urlRepository;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("A", HttpStatus.OK);
    }

    @GetMapping("/url/{hash}")
    public ResponseEntity<?> getUrl(@PathVariable(value = "hash") String hash) {
        UrlModel urlModel = urlRepository.findByShortUrl(hash);
        if (urlModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"menssagem\": \"Not Found\"}");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(urlModel);
    }

    @PostMapping("/url")
    public UrlModel createUrl(@RequestBody @Valid UrlModel urlModel) {
        return urlRepository.save(urlModel);
    }

}
