package com.wissen.url_shortening.repository;

import com.wissen.url_shortening.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    public Url findByShortLink(String shortLink);
}
