package com.example.url_shortener.Services;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.url_shortener.Entities.Url;
import com.example.url_shortener.Repositories.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final StringRedisTemplate redisTemplate;
    private final UrlRepository urlRepository;

    public Url shortenLongUrl(String originalUrl){
        if(!isValidUrl(originalUrl)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL format");
        }
        Optional<Url> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if(existingUrl.isPresent()){
            return existingUrl.get();
        }
        String shortUrl = generateShortHash(originalUrl);
        redisTemplate.opsForValue().set(shortUrl, originalUrl);
        Url url = new Url(originalUrl, shortUrl);
        urlRepository.save(url);
        return url;
    }

    public Url getOriginalUrl(String shortUrl){
        String originalUrl = redisTemplate.opsForValue().get(shortUrl);
        if(originalUrl == null){
            Url url = urlRepository.findByShortUrl(shortUrl).orElse(null);
            if(url == null){
                return null;
            }
            originalUrl = url.getOriginalUrl();
        }
        return new Url(originalUrl, shortUrl);
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
