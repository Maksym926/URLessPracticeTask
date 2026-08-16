package com.test.gateway;

import com.test.shortener.ShortenedURL;

import java.util.Optional;

public interface UrlGateway {
    Optional<ShortenedURL> getById(String id);

    void create(String url, String id);
}
