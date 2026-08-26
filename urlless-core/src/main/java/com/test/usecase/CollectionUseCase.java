package com.test.usecase;

import com.test.collection.UrlCollection;
import com.test.shortener.ShortenedURL;

import java.util.List;
import java.util.Optional;

public interface CollectionUseCase {
    Optional<UrlCollection> getById(String id);

    UrlCollection create(List<ShortenedURL> shortenedUrls);
}
