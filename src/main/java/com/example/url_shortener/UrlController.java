package com.example.url_shortener;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/urls")
public class UrlController {

    @Autowired
    UrlService urlService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String shortenUrl(@RequestBody String originalUrl) {
        String shortUrl = urlService.shortenLongUrl(originalUrl);
        return shortUrl;
    }

    @GetMapping("/{shortUrl}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String resolveUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        return originalUrl;
    }


}
