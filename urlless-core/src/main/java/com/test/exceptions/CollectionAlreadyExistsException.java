package com.test.exceptions;

import lombok.Getter;

@Getter
public class CollectionAlreadyExistsException extends RuntimeException{
    private final String id;
    public CollectionAlreadyExistsException(String id){
        super("Collection with id: " + id + " already exists");
        this.id = id;
    }
}
