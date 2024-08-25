package com.wissen.url_shortening.controller;

import com.wissen.url_shortening.model.Url;
import com.wissen.url_shortening.model.UrlDto;
import com.wissen.url_shortening.model.UrlErrorResponseDto;
import com.wissen.url_shortening.model.UrlResponseDto;
import com.wissen.url_shortening.service.UrlService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlShorteningController {

    @Autowired
    private UrlService urlService;
    @Autowired
    private UrlResponseDto urlResponseDto;
    @Autowired
    private UrlErrorResponseDto urlErrorResponseDto;

    @PostMapping("/generate")
    public ResponseEntity<?> generateShortLink(@RequestBody UrlDto urlDto) {
        Url urlToRet = urlService.generateShortLink(urlDto);
        //if url object that need to prepare(according to some format) is not null, then return UrlReponse object
        if(urlToRet != null) {
            urlResponseDto.setOriginalUrl(urlToRet.getOriginalUrl());
            urlResponseDto.setExpirationDate(urlToRet.getExpirationDate());
            urlResponseDto.setShortLink(urlToRet.getShortLink());
            return new ResponseEntity<UrlResponseDto>(urlResponseDto, HttpStatus.OK);
        }
        //we are not able to generate the short link, So prepare url Error Reponse object & return it
        urlErrorResponseDto.setStatus("404");
        urlErrorResponseDto.setError("There was an error processing your request, " +
                "please try again");
        return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response) {
        //if passed short is empty
        if(StringUtils.isEmpty(shortLink)) {
            urlErrorResponseDto.setStatus("404");
            urlErrorResponseDto.setError("Invalid url");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }

        Url urlToReturn = urlService.getEncodedUrl(shortLink);
        //if url is expired does not exist due to some other reason
        if(urlToReturn == null) {
            urlErrorResponseDto.setError("url does not exist or it might have expired");
            urlErrorResponseDto.setStatus("400");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }
        //if url has been expired
        if(urlToReturn.getExpirationDate().isBefore(LocalDateTime.now())) {
            //delete the url because it is expired
            urlService.deleteShortLink(urlToReturn);
            urlErrorResponseDto.setStatus("Url expired. please generate a new one");
            urlErrorResponseDto.setError("200");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }

        // Redirect to the original URL
        try {
            //Sends a redirect response to the client using the specified redirect
            // location URL with the status code SC_FOUND 302 (Found), clears the
            // response buffer and commits the response
            response.sendRedirect(urlToReturn.getOriginalUrl());
        } catch (IOException e) {
            urlErrorResponseDto.setStatus("500");
            urlErrorResponseDto.setError("Error during redirection");
            return new ResponseEntity<UrlErrorResponseDto>(urlErrorResponseDto, HttpStatus.OK);
        }

        return null; // This line will never be reached
    }

    @DeleteMapping("/{shortLink}")
    public void deleteShortUrl(@PathVariable String shortLink) {
        Url urlToDelete = urlService.getEncodedUrl(shortLink);
        urlService.deleteShortLink(urlToDelete);
    }

}
