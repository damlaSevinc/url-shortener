package com.example.url_shortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.example.url_shortener.Entities.Url;
import com.example.url_shortener.Repositories.UrlRepository;
import com.example.url_shortener.Services.UrlService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @MockBean
    private UrlRepository urlRepository;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Autowired
    private UrlService urlService;


    @Test
    public void shortenLongUrl_returnsShortenedUrl() {
        String originalUrl = "https://www.example.com";
        String shortUrl = "e149be1";

        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(Optional.empty());

        Url savedUrl = new Url(originalUrl, shortUrl);
        when(urlRepository.save(any(Url.class))).thenReturn(savedUrl);

        Url result = urlService.shortenLongUrl(originalUrl);

        assertNotNull(result);
        assertEquals(originalUrl, result.getOriginalUrl());
        assertEquals(shortUrl, result.getShortUrl());
    }

    @Test
    public void resolveUrl_returnsOriginalUrl() {
        String shortUrl = "e15adb8";
        String originalUrl = "https://dkbcodefactory.com";

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(new Url(originalUrl, shortUrl)));

        Url result = urlService.getOriginalUrl(shortUrl);

        assertNotNull(result);
        assertEquals(originalUrl, result.getOriginalUrl());
    }

    @Test
    public void isValidUrl_returnsTrue() {
        String url = "https://www.example.com";
        boolean result = urlService.isValidUrl(url);
        assertEquals(true, result);
    }
}
