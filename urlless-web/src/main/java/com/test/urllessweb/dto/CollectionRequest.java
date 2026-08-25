package com.test.urllessweb.dto;

import com.test.shortener.ShortenedURL;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionRequest {
    private List<ShortenedURL> shortenedUrls;
}
