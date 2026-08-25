package com.test.shortener;

import lombok.*;


@Getter
@RequiredArgsConstructor

@AllArgsConstructor
@Builder
public class ShortenedURL {
    private String url;

    private  String Id;

}
