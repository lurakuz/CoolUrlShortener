package com.kuz9.urlshortener.controller;

import com.kuz9.urlshortener.service.UrlService;
import com.kuz9.urlshortener.service.UrlServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * Provides API for Url related operations
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UrlController {

    private final UrlService urlService;

    /**
     * Provides API to get short URL from a long one
     * @param longUrl to convert to a short one
     * @return short URL
     */
    @Cacheable(value = "url")
    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping("create-short")
    public String convertToShortUrl(@RequestParam String longUrl) {
        log.info("Converting longUrl = {}", longUrl);
        String shortUrl = urlService.getShortUrl(longUrl);
        log.info("Converted longUrl = {} to shortUrl = {}", longUrl, shortUrl);
        return shortUrl;
    }

    /**
     * Provides API to get long URL from a short one if exist
     * @param shortUrl to find long one
     * @return long URL
     */
    @Cacheable(value = "url")
    @SneakyThrows
    @ApiOperation(value = "Return long Url", notes = "Finds original url from short url")
    @GetMapping(value = "return-long")
    public String getOriginalURL(@RequestParam String shortUrl) {
        log.info("Getting long url by short url = {}", shortUrl);
        return urlService.getOriginalUrl(shortUrl);
    }
}
