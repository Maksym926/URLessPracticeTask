package com.test.urllessweb.controllers;

import com.test.collection.UrlCollection;
import com.test.urllessweb.dto.CollectionRequest;
import com.test.urllessweb.dto.CollectionResponse;
import com.test.usecase.CollectionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/collection")
public class CollectionController {

    private final CollectionUseCase collectionUseCase;

    public CollectionController(CollectionUseCase collectionUseCase){
        this.collectionUseCase = collectionUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity getCollection(@PathVariable(value = "id") String id){
        return collectionUseCase.getById(id)
                .map(s -> ResponseEntity.ok(s.getShortenedURLS()))
                .orElseGet(() -> ResponseEntity.notFound().build());


    }
    @PostMapping
    public ResponseEntity postCollection(@RequestBody CollectionRequest request){
        UrlCollection collection = collectionUseCase.create(request.getShortenedUrls());
        String location = "/api/collections/" + collection.getId();
        return ResponseEntity.created(URI.create(location)).body(new CollectionResponse(request.getShortenedUrls(), collection.getShortenedURLS()));
    }
}
