package com.test.gateway;

import com.test.collection.UrlCollection;
import com.test.exceptions.CollectionAlreadyExistsException;
import com.test.shortener.ShortenedURL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CollectionGatewayFake  implements CollectionGateway{

    Map<String, UrlCollection> collections = new HashMap<>();

    @Override
    public UrlCollection create(String id, List<ShortenedURL> shortenedURLS) {
        if(collections.containsKey(id))
            throw new CollectionAlreadyExistsException(id);
        UrlCollection collection = new UrlCollection(id, shortenedURLS);
        collections.put(id, collection);
        return collection;
    }

    @Override
    public Optional<UrlCollection> getById(String id) {
        return Optional.ofNullable(collections.get(id));
    }

    @Override
    public List<UrlCollection> getAll() {
        return collections.values().stream().toList();
    }
}
