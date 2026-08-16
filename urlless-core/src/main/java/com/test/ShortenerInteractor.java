package com.test;

import java.util.Optional;

public class    ShortenerInteractor {

    private UrlGateway urlGateway;

    public ShortenerInteractor(UrlGateway urlGateway) {
        this.urlGateway = urlGateway;
    }

    public Optional<ShortenedURL> getById(String id) {
        return urlGateway.getById(id);
    }

    public void create(String url, String id) {
        urlGateway.create(url, id);
    }
}
