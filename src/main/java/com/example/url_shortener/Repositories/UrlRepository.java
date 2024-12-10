package com.example.url_shortener.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.url_shortener.Entities.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{

    Optional<Url> findByOriginalUrl(String originalUrl);
    Optional<Url> findByShortUrl(String shortUrl);

}
