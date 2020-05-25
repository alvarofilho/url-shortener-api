package me.alvarofilho.urlshortener.utils;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

@Service
public class UrlUtils {

    public boolean pingURL(String url) {
        url = url.replaceFirst("^https", "http");
        try {
            var httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            var responseCode = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString()).statusCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException | InterruptedException | IllegalArgumentException exception) {
            return false;
        }
    }

    public String createRandomUrl() {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
