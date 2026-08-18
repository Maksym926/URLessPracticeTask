package com.test.gateway;

import com.test.collection.UrlCollection;
import com.test.shortener.ShortenedURL;

import java.util.List;
import java.util.Optional;

public interface CollectionGateway {
    UrlCollection create(String id, List<ShortenedURL> shortenedURLS);

    Optional<UrlCollection> getById(String id);
}
