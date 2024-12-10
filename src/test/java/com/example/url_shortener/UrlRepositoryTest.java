package com.example.url_shortener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.url_shortener.Entities.Url;
import com.example.url_shortener.Repositories.UrlRepository;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    public void cleanUp(){
        urlRepository.deleteAll();
    }

    @Test
    void findByShortUrl_whenShortUrlDoesNotExist_returnsEmpty(){
        Optional<Url> originalUrl = urlRepository.findByShortUrl("not-exist");
        assertThat(originalUrl).isEmpty();
    }

    @Test
    void findByShortUrl_whenShortUrlExists_returnsOriginalUrl(){
        List<Url> givenUrls = asList(
                new Url(
                    "https://www.example.com",
                    "c984d06"
                    ),
                new Url(
                    "https://www.google.com",
                    "8ffdefb"
                    )
        );
        urlRepository.saveAll(givenUrls);

        List<Url> actualUrls = urlRepository.findAll();

        assertThat(actualUrls).hasSize(2);
        assertThat(actualUrls.get(0).getOriginalUrl()).isEqualTo("https://www.example.com");
        assertThat(actualUrls.get(0).getShortUrl()).isEqualTo("c984d06");
        assertThat(actualUrls.get(1).getOriginalUrl()).isEqualTo("https://www.google.com");
        assertThat(actualUrls.get(1).getShortUrl()).isEqualTo("8ffdefb");
    }

    @Test
    void findByOriginalUrl_whenOriginalUrlDoesNotExist_returnsEmpty(){
        Optional<Url> shortUrl = urlRepository.findByOriginalUrl("url-not-exist");
        assertThat(shortUrl).isEmpty();
    }

    @Test
    void findByOriginalUrl_whenOriginalUrlExists_returnsShortlUrl(){
        List<Url> givenUrls = asList(
                new Url(
                    "https://dkbcodefactory.com",
                    "e15adb8"
                    ),
                new Url(
                    "https://www.google.com.tr",
                    "e688591"
                    )
        );
        urlRepository.saveAll(givenUrls);

        List<Url> actualUrls = urlRepository.findAll();

        assertThat(actualUrls).hasSize(2);
        assertThat(actualUrls.get(0).getOriginalUrl()).isEqualTo("https://dkbcodefactory.com");
        assertThat(actualUrls.get(0).getShortUrl()).isEqualTo("e15adb8");
        assertThat(actualUrls.get(1).getOriginalUrl()).isEqualTo("https://www.google.com.tr");
        assertThat(actualUrls.get(1).getShortUrl()).isEqualTo("e688591");
    }
}
