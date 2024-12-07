package com.example.url_shortener;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

    public String shortenLongUrl(String longUrl){
        return "shortUrl";
    }

    public String getOriginalUrl(String shortUrl){
        return "originalUrl";
    }
}
