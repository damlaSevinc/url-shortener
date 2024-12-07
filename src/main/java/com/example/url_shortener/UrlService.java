package com.example.url_shortener;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UrlService {

    public String shortenLongUrl(String longUrl){
        if(!isValidUrl(longUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL format");
        }
        return "shortUrl";
    }

    public String getOriginalUrl(String shortUrl){
        return "originalUrl";
    }

    public boolean isValidUrl(String url){
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }
}
