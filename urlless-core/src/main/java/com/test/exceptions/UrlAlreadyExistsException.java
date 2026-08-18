package com.test.exceptions;

import lombok.Getter;

@Getter
public class UrlAlreadyExistsException extends  RuntimeException{
    private final String id;
    public UrlAlreadyExistsException(String id){
        super("Url " + id + " already exists");
        this.id = id;

    }
}
