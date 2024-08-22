package com.wissen.url_shortening.model;

import java.time.LocalDateTime;

/*
 * response that we will return to the user*/
public class UrlResponseDto {

    private String originalLink;
    private String shortLink;
    private LocalDateTime expirationDate;
}
