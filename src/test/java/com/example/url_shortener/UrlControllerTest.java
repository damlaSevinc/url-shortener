package com.example.url_shortener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@AutoConfigureWebMvc
@SpringBootTest
public class UrlControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private UrlService urlService;

    @Mock
    private Url url;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void resolvelUrl_whenOriginalUrlExists_returnOriginalUrl() throws Exception {
        String shortUrl = "e688591";
        Url originalUrl = new Url("https://www.google.com.tr", shortUrl);

        this.mockMvc.perform(get("/api/urls/{shortUrl}", shortUrl))
            .andExpectAll(
                status().isMovedPermanently(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.originalUrl").value(originalUrl.getOriginalUrl())
            );
    }

    @Test
    public void resolveUrl_whenOriginalUrlDoesNotExist_returnNotFound() throws Exception {
        String shortUrl = "non-existing-url";

        this.mockMvc.perform(get("/api/urls/{shortUrl}", shortUrl))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shortenUrl_whenValidUrl_returnShortUrl() throws Exception {
        UrlRequestDto urlRequestDto = new UrlRequestDto();
        urlRequestDto.setOriginalUrl("https://dkbcodefactory.com");
        Url url = new Url("https://dkbcodefactory.com", "e15adb8");

        this.mockMvc.perform(post("/api/urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(urlRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.shortUrl").value(url.getShortUrl()));
    }

    @Test
    public void shortenUrl_whenInvalidUrl_returnBadRequest() throws Exception {
        UrlRequestDto urlRequestDto = new UrlRequestDto();
        urlRequestDto.setOriginalUrl("invalid-url");

        this.mockMvc.perform(post("/api/urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(urlRequestDto)))
            .andExpect(status().isBadRequest());
    }

}
