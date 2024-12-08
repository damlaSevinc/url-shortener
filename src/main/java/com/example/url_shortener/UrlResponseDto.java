package com.example.url_shortener;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlResponseDto {

    private String originalUrl;
    private String shortUrl;

}
