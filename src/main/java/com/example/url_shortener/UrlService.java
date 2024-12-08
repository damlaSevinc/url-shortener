package com.example.url_shortener;

import java.util.UUID;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UrlService {

    private final StringRedisTemplate redisTemplate;

    public UrlService(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public String shortenLongUrl(String originalUrl){
        if(!isValidUrl(originalUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL format");
        }
        String hash = generateShortHash(originalUrl);
        redisTemplate.opsForValue().set(hash, originalUrl);
        return hash;
    }

    public String getOriginalUrl(String shortUrl){
        String originalUrl = redisTemplate.opsForValue().get(shortUrl);
        if(originalUrl == null){
            throw new IllegalArgumentException("URL not found");
        }
        return originalUrl;
    }

    public boolean isValidUrl(String url){
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

    public String generateShortHash(String originalUrl){
        UUID uuid = UUID.nameUUIDFromBytes(originalUrl.getBytes());
        return uuid.toString().substring(0, 7);
    }
}
