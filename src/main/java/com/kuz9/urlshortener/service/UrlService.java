package com.kuz9.urlshortener.service;

public interface UrlService {
    String getShortUrl(String longUrl);
    String getOriginalUrl(String shortUrl);
}
