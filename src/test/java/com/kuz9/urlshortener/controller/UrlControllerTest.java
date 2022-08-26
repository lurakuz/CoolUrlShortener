package com.kuz9.urlshortener.controller;

import com.kuz9.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;


    @Test
    void shouldReturnOriginalURL() throws Exception {
        //given
        when(urlService.getOriginalUrl("s1")).thenReturn("r1");

        //when
        //then
        mockMvc.perform(get("/api/v1/return-long?shortUrl=s1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("r1")));
    }
}