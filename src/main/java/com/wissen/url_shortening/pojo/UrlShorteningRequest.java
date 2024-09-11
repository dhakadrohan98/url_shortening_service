package com.wissen.url_shortening.pojo;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UrlShorteningRequest {

    private String url;
    private String expirationDate;

    public UrlShorteningRequest(String originalUrl, String expirationDate) {
        this.url = originalUrl;
        this.expirationDate = expirationDate;
    }
}
