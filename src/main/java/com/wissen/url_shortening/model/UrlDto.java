package com.wissen.url_shortening.model;

import lombok.*;

/*
 * // we want this data from user to generate shorten url
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlDto {
    private String url;
    private String expirationDate; //optional

}
