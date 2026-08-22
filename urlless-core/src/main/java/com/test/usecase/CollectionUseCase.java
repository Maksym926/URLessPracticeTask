package com.test.usecase;

import com.test.collection.UrlCollection;

import java.util.Optional;

public interface CollectionUseCase {
    Optional<UrlCollection> getById(String id);
}
