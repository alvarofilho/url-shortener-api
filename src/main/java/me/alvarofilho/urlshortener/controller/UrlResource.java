package me.alvarofilho.urlshortener.controller;

import me.alvarofilho.urlshortener.model.UrlModel;
import me.alvarofilho.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlResource {

    @Autowired
    UrlRepository urlRepository;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<String>("A", HttpStatus.OK);
    }

    @GetMapping("/url/{hash}")
    public ResponseEntity<?> getUrl(@PathVariable(value = "hash") String hash) {
        UrlModel urlModel = urlRepository.findByUrlRandom(hash);
        if (urlModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"menssagem\": \"Not Found\"}");
        }
        return ResponseEntity.ok().body(urlModel);
    }

    @PostMapping("/url")
    public UrlModel createUrl(@RequestBody @Validated UrlModel urlModel){
        return urlRepository.save(urlModel);
    }

}
