package com.wissen.url_shortening.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class UrlErrorResponseDto {
    private String status;
    private String error;
}
