package com.kuz9.urlshortener.service;

import com.kuz9.urlshortener.entity.Url;
import com.kuz9.urlshortener.exception.EntityNotFoundException;
import com.kuz9.urlshortener.exception.UrlValidationException;
import com.kuz9.urlshortener.repository.UrlRepository;
import com.sangupta.murmur.Murmur3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    /**
     * Get or create short URL string if sent URL is correct
     * @param longUrl to get or to create short URL
     * @return shortUrl
     * @throws UrlValidationException if user sent inappropriate longUrl
     */
    @Override
    public String getShortUrl(String longUrl) {
        if (!longUrl.startsWith("https://www.") & !longUrl.startsWith("http://www."))
            throw new UrlValidationException("Pass url to receive short one please!");
        var url = urlRepository.findByLongUrl(longUrl);
        log.info("Fetched url from db : {}", url);
        if (url.isEmpty())
            return createNewUrlEntity(longUrl).getShortUrl();
        return url.get().getShortUrl();
    }

    /**
     * Get original URL if user sent correct short one
     * @param shortUrl to find original URL in database
     * @return long URL
     * @throws EntityNotFoundException if no Url was found
     */
    @Override
    public String getOriginalUrl(String shortUrl) {
        if (!shortUrl.startsWith("kuz.ia/"))
            throw new UrlValidationException("Pass short url to receive long one please!");
        return urlRepository.findByShortUrl(shortUrl).orElseThrow(() ->
                new EntityNotFoundException(Url.class, "shortUrl", shortUrl)).getLongUrl();
    }

    /**
     * Creating new URL entity
     * @param longUrl to create a short one
     * @return URL entity
     */
    private Url createNewUrlEntity(String longUrl) {
        var url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(createShortUrl(longUrl));
        urlRepository.save(url);
        return url;
    }

    /**
     * Creating short URL from a long one
     * @param longUrl to hash into short one
     * @return shortURL
     */
    private String createShortUrl(String longUrl) {

        String hash = Arrays.toString(Murmur3.hash_x64_128(longUrl.getBytes(), longUrl.length(), 0));

        hash = hash.replace("[", "").replace("]", "")
                .replace(",", "").replace(" ", "").replace("-", "");

        return "kuz.ia/" + hash;
    }
}
