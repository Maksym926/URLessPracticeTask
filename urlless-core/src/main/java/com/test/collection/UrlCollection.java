package com.test.collection;

import com.test.shortener.ShortenedURL;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UrlCollection {
    private String id;
    private List<ShortenedURL> shortenedURLS;
}
