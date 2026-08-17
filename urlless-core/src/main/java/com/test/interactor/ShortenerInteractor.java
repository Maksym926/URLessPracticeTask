package com.test.interactor;

import com.test.generator.IdGenerator;
import com.test.shortener.ShortenedURL;
import com.test.gateway.UrlGateway;
import com.test.usecase.ShortenerUseCase;

import java.util.Optional;

public class    ShortenerInteractor implements ShortenerUseCase {

    private final UrlGateway urlGateway;
    private final IdGenerator generator;

    public ShortenerInteractor(UrlGateway urlGateway, IdGenerator generator) {
        this.urlGateway = urlGateway;
        this.generator = generator;
    }

    public Optional<ShortenedURL> getById(String id) {
        return urlGateway.getById(id);
    }

    public ShortenedURL create(String url) {
        String id = generator.generate();
        return urlGateway.create(url, id);
    }
}
