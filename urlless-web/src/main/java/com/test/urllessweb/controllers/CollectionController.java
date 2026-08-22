package com.test.urllessweb.controllers;

import com.test.usecase.CollectionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
