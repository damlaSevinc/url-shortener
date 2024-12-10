package com.example.url_shortener;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

import com.example.url_shortener.Controllers.UrlController;
import com.example.url_shortener.DTOs.UrlRequestDto;
import com.example.url_shortener.Entities.Url;
import com.example.url_shortener.Services.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UrlController.class)
public class WebControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
    private UrlService urlService;

    @Autowired
    private ObjectMapper objectMapper;

	@Test
	public void shortenUrl() throws Exception {
        UrlRequestDto urlRequestDto = new UrlRequestDto();
        urlRequestDto.setOriginalUrl("https://example.com");
        Url url = new Url("https://example.com", "test");
        when(urlService.shortenLongUrl(urlRequestDto.getOriginalUrl())).thenReturn(url);

    mockMvc.perform(post("/api/urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(urlRequestDto)))
            .andExpect(status().isCreated());
	}

    @Test
    public void resolveUrl() throws Exception {
        String shortUrl = "test";
        Url url = new Url("https://example.com", shortUrl);
        when(urlService.getOriginalUrl(shortUrl)).thenReturn(url);

        mockMvc.perform(get("/api/urls/{shortUrl}", shortUrl))
            .andExpect(status().isOk());
    }
}
