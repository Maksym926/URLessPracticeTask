package com.test.interactor;

import com.test.exception.FailedToCreateUrlException;
import com.test.exception.UrlAlreadyExistsException;
import com.test.generator.IdGenerator;
import com.test.shortener.ShortenedURL;
import com.test.gateway.UrlGateway;
import com.test.usecase.ShortenerUseCase;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        boolean collision = false;

        Set<String> collisions = new HashSet<>();
        do{
            try{
                String id = generator.generate(url, collisions);
                return urlGateway.create(url, id);
            }catch (UrlAlreadyExistsException e){
                collision = true;
                collisions.add(e.getId());
            }
        }while (collision);

        throw new FailedToCreateUrlException();
    }
}
