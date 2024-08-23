package com.wissen.url_shortening.model;

import lombok.*;

import java.time.LocalDateTime;

/*
 * response that we will return to the user*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlResponseDto {

    private String originalUrl;
    private String shortLink;
    private LocalDateTime expirationDate;
}
