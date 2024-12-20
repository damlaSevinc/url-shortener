package com.example.url_shortener.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.url_shortener.DTOs.UrlRequestDto;
import com.example.url_shortener.DTOs.UrlResponseDto;
import com.example.url_shortener.Entities.Url;
import com.example.url_shortener.Services.UrlService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponseDto shortenUrl(@RequestBody UrlRequestDto urlRequestDto) {
        Url url = urlService.shortenLongUrl(urlRequestDto.getOriginalUrl());
        UrlResponseDto urlResponseDto = new UrlResponseDto();
        urlResponseDto.setShortUrl(url.getShortUrl());
        return urlResponseDto;
    }

    @GetMapping("/{shortUrl}")
    @ResponseStatus(HttpStatus.OK)
    public UrlResponseDto resolveUrl(@PathVariable String shortUrl) {
        Url originalUrl = urlService.getOriginalUrl(shortUrl);
        if(originalUrl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short URL not found");
        }
        UrlResponseDto urlResponseDto = new UrlResponseDto();
        urlResponseDto.setOriginalUrl(originalUrl.getOriginalUrl());
        return urlResponseDto;
    }


}
