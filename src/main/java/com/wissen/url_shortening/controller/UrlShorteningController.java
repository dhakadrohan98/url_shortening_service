package com.wissen.url_shortening.controller;

import com.wissen.url_shortening.model.Url;
import com.wissen.url_shortening.model.UrlDto;
import com.wissen.url_shortening.model.UrlErrorResponseDto;
import com.wissen.url_shortening.model.UrlResponseDto;
import com.wissen.url_shortening.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShorteningController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
        Url urlToRet = urlService.generateShortLink(urlDto);
        //if url object that need to prepare(accoding to some format) is not null, then return UrlReponse object
        if(urlToRet != null) {
            UrlResponseDto urlResponseDto = new UrlResponseDto();
            urlResponseDto.setOriginalUrl(urlToRet.getOriginalUrl());
            urlResponseDto.setExpirationDate(urlToRet.getExpirationDate());
            urlResponseDto.setShortLink(urlToRet.getShortLink());
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
        //we are not able to generate the short link, So prepare url Error Reponse object & return it
        UrlErrorResponseDto urlErrorResponseDto = new UrlErrorResponseDto();
        urlErrorResponseDto.setStatus("404");
        urlErrorResponseDto.setError("There was an error processing your request, " +
                "please try again");
        return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
    }
}
