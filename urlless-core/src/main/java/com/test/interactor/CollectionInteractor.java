package com.test.interactor;

import com.test.collection.UrlCollection;
import com.test.exceptions.CollectionAlreadyExistsException;
import com.test.exceptions.FailedToCreateUrlException;
import com.test.gateway.CollectionGateway;
import com.test.generator.IdGenerator;
import com.test.shortener.ShortenedURL;
import com.test.usecase.CollectionUseCase;
import com.test.usecase.ShortenerUseCase;

import java.util.*;

public class CollectionInteractor implements CollectionUseCase {

    private final CollectionGateway collectionGateway;

    private final ShortenerUseCase shortenerInteractor;

    private final IdGenerator idGenerator;

    public CollectionInteractor(CollectionGateway collectionGateway, ShortenerInteractor shortenerInteractor, IdGenerator idGenerator) {
        this.collectionGateway = collectionGateway;
        this.shortenerInteractor = shortenerInteractor;
        this.idGenerator = idGenerator;


    }

    public Optional<UrlCollection> getById(String id) {
        return collectionGateway.getById(id);
    }

    @Override
    public UrlCollection create(List<ShortenedURL> urls) {

        List<ShortenedURL> shortenedUrls = shortenUrlsInCollections(urls);

        boolean collision = false;

        Set<String> collisions = new HashSet<>();
        do{
            try{
                String id = idGenerator.generate("", collisions);
                return collectionGateway.create(id, shortenedUrls);
            }catch (CollectionAlreadyExistsException e){
                collision = true;
                collisions.add(e.getId());
            }
        }while (collision);

        throw new FailedToCreateUrlException();

    }

    private List<ShortenedURL> shortenUrlsInCollections(List<ShortenedURL> urls) {
        List<ShortenedURL> shortened = new ArrayList<>();
        for (ShortenedURL u : urls){
            if(u.getId() == null || Objects.equals(shortenerInteractor.getById(u.getId()), Optional.empty())){
                shortened.add(shortenerInteractor.create(u.getUrl()));
            }else{
                shortened.add(u);
            }
        }
        return shortened;
    }
}
