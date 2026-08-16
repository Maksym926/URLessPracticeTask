package com.test.urllessweb.controller;

import com.test.shortener.ShortenedURL;
import com.test.urllessweb.dto.CreateUrlRequest;
import com.test.urllessweb.dto.CreateUrlResponse;
import com.test.usecase.ShortenerUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    @PostMapping("/")
    public ResponseEntity post(@RequestBody CreateUrlRequest request){
        ShortenedURL shortenedURL = shortenerUseCase.create(request.getUrl());
        String url = "http://urle.ss/" + shortenedURL.getId();

        return  ResponseEntity.created(URI.create(url))
                .body(new CreateUrlResponse(url, shortenedURL.getUrl()));
    }
}
