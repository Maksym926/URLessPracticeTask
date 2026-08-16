package com.test.urllessweb.controller;

import com.test.usecase.ShortenerUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class ShortenerController {

    private final ShortenerUseCase shortenerUseCase;

    public ShortenerController(ShortenerUseCase shortenerUseCase) {
        this.shortenerUseCase = shortenerUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable(value = "id") String id){

        return shortenerUseCase.getById(id)
                .map(s-> ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .header("Location", s.getUrl()).build())
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND).body("url does not exists"));

    }
}
