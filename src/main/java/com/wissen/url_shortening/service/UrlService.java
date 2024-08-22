package com.wissen.url_shortening.service;
import com.wissen.url_shortening.model.Url;
import com.wissen.url_shortening.model.UrlDto;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {
    public Url generateShortLink(UrlDto urlDto);

    public Url persistShortLink(Url url);

    public Url getEncodedUrl(String url);

    public void deleteShortLink(Url url);
}
