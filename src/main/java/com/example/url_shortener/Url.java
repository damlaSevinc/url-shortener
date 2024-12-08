package com.example.url_shortener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String originalUrl;

    @Column(nullable = false, unique = true)
    private String shortUrl;

    public Url(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }
}
