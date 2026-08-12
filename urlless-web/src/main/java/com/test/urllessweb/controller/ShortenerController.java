package com.test.urllessweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class ShortenerController {
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("url does not exists");
    }
}
