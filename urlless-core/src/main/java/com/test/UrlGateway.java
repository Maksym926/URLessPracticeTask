package com.test;

import java.util.Optional;

public interface UrlGateway {
    Optional<ShortenedURL> getById(String id);

    void create(String url, String id);
}
