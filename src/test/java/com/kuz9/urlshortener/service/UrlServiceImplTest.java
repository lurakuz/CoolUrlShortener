package com.kuz9.urlshortener.service;

import com.kuz9.urlshortener.entity.Url;
import com.kuz9.urlshortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class UrlServiceImplTest {

    @Mock
    UrlRepository urlRepository;

    @InjectMocks
    UrlServiceImpl urlService;


    @Test
    public void shouldReturnExistingShortUrl() {
        //given
        var longUrl = "https://meet.google.com/1";
        var expectedShortUrl = "https://mee/67572454286670320834647382355638762720";
        when(urlRepository.findByLongUrl(longUrl)).thenReturn(Optional.of(new Url(longUrl, expectedShortUrl)));

        //when
        String actualShortUrl = urlService.getShortUrl(longUrl);
        log.info("actualShortUrl = {}", actualShortUrl);

        //then
        assertEquals(expectedShortUrl, actualShortUrl);
        verify(urlRepository, times(1)).findByLongUrl(longUrl);
        verify(urlRepository, times(0)).save(any());
    }

    @Test
    public void shouldCreateAndReturnShortUrl() {
        //given
        var longUrl = "https://meet.google.com/1";
        var expectedShortUrl = "https://mee/67572454286670320834647382355638762720";
        Url url = new Url(longUrl, expectedShortUrl);
        when(urlRepository.findByLongUrl(longUrl)).thenReturn(Optional.empty());
        when(urlRepository.save(url)).thenReturn(url);

        //when
        String actualShortUrl = urlService.getShortUrl(longUrl);
        log.info("actualShortUrl = {}", actualShortUrl);

        //then
        assertEquals(expectedShortUrl, actualShortUrl);
        verify(urlRepository, times(1)).findByLongUrl(longUrl);
        verify(urlRepository, times(1)).save(any());
    }

}
