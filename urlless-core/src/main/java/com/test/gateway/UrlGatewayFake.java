package com.test.gateway;

import com.test.shortener.ShortenedURL;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UrlGatewayFake implements UrlGateway {

    Map<String, ShortenedURL> urls = new HashMap<>();

    @Override
    public Optional<ShortenedURL> getById(String id) {
        return Optional.ofNullable(urls.get(id));
    }

    @Override
    public void create(String url, String id) {
        ShortenedURL res = new ShortenedURL(url, id);
        urls.put(id, res);
    }
}
