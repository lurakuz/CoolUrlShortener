package com.kuz9.urlshortener.service;

import com.kuz9.urlshortener.entity.Url;
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

    @Override
    public String getShortUrl(String longUrl) {
        var url = urlRepository.findByLongUrl(longUrl);
        log.info("Fetched url from db : {}", url);
        if (url.isEmpty())
            return createNewUrlEntity(longUrl).getShortUrl();
        return url.get().getShortUrl();
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl).orElse(new Url()).getLongUrl();
    }

    private Url createNewUrlEntity(String longUrl) {
        var url = new Url();
        url.setLongUrl(longUrl);
        url.setShortUrl(createShortUrl(longUrl));
        urlRepository.save(url);
        return url;
    }

    private String createShortUrl(String longUrl) {

        String hash = Arrays.toString(Murmur3.hash_x64_128(longUrl.getBytes(), longUrl.length(), 0));

        hash = hash.replace("[", "").replace("]", "")
                .replace(",", "").replace(" ", "").replace("-", "");

        return longUrl.substring(0, 11) + "/" + hash;
    }
}
