package me.alvarofilho.urlshortener.repository;

import me.alvarofilho.urlshortener.model.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlModel, Long> {

    UrlModel findByShortUrl(String shortUrl);

}
