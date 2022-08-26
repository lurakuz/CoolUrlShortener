package com.kuz9.urlshortener.repository;

import com.kuz9.urlshortener.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, Long> {

    Optional<Url> findByLongUrl(String longUrl);

    Optional<Url> findByShortUrl(String shortUrl);
}
