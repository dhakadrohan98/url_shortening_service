package com.wissen.url_shortening.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UrlErrorResponseDto {
    private String status;
    private String error;
}
