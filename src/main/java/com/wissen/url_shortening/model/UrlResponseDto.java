package com.wissen.url_shortening.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/*
 * response that we will return to the user*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class UrlResponseDto {

    private String originalUrl;
    private String shortLink;
    private LocalDateTime expirationDate;
}
