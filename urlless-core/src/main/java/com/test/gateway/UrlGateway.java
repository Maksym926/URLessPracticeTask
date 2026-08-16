package com.test.gateway;

import com.test.shortener.ShortenedURL;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UrlGateway {
    Optional<ShortenedURL> getById(String id);

    ShortenedURL create(String url, String id);

    List<ShortenedURL> getAll();
}
