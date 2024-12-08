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
    public UrlResponseDto shortenUrl(@RequestBody UrlRequestDto urlRequestDto) {
        Url url = urlService.shortenLongUrl(urlRequestDto.getOriginalUrl());
        UrlResponseDto urlResponseDto = new UrlResponseDto();
        urlResponseDto.setShortUrl(url.getShortUrl());
        return urlResponseDto;
    }

    @GetMapping("/{shortUrl}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public UrlResponseDto resolveUrl(@PathVariable String shortUrl) {
        Url originalUrl = urlService.getOriginalUrl(shortUrl);
        UrlResponseDto urlResponseDto = new UrlResponseDto();
        urlResponseDto.setOriginalUrl(originalUrl.getOriginalUrl());
        return urlResponseDto;
    }


}
