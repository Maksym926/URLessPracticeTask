package com.test.interactor;

import com.test.collection.UrlCollection;
import com.test.gateway.CollectionGateway;

import java.util.Optional;

public class CollectionInteractor {

    private final CollectionGateway collectionGateway;

    public CollectionInteractor(CollectionGateway collectionGateway) {
        this.collectionGateway = collectionGateway;
    }

    public Optional<UrlCollection> getById(String id) {
        return collectionGateway.getById(id);
    }
}
