package com.wissen.url_shortening.service;

import ch.qos.logback.core.util.StringUtil;
import com.google.common.hash.Hashing;
import com.wissen.url_shortening.model.Url;
import com.wissen.url_shortening.model.UrlDto;
import io.micrometer.common.util.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class UrlServiceImpl implements UrlService {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public Url generateShortLink(UrlDto urlDto) {
        String originalUrl = urlDto.getUrl();
        if (StringUtils.isNotEmpty(originalUrl)) {
            // Generate a short URL
            String shortUrl = generateShortUrl(originalUrl);
        }
        return null;
    }

    //Since MD5 produces a large hash and we're truncating it to 7 characters,
    // there's a small chance of collisions (different URLs generating the same short URL).
    // This can be mitigated by checking for collisions or by using a different hash
    // function with a larger bit size.
    //using md5() algorithm & writing custom base62Encoded() method logic
    private String generateShortUrl(String originalUrl) {
        //Generate 128-bit hash value (32 hexadecimal characters) from the original URL
        // using MD5 algorithm
        String hash = Hashing.md5().hashString(originalUrl, StandardCharsets.UTF_8).toString();
        //convert hash to BigInteger to handle large numbers
        BigInteger hashNumber = new BigInteger(hash, 16);

        //Base62 encoding of the hash (custom method)
        String base62Encoded = base62Encoded(hashNumber);

        //take the first 7 characters
        return base62Encoded.substring(0,7);
    }

    private String base62Encoded(BigInteger hashNumber) {
        StringBuilder encodedString = new StringBuilder();

        while(hashNumber.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] quotientAndRemainder = hashNumber.divideAndRemainder(BigInteger.valueOf(62));
            // quotientAndRemainder[0] =  hashNumber/62;
            // quotientAndRemainder[1] = hashNumber%62;
            encodedString.append(BASE62.charAt(quotientAndRemainder[1].intValue()));
            hashNumber  = quotientAndRemainder[0];
        }
        //reverse the string as encoding produces the least significant digit first
        return encodedString.reverse().toString();
    }

    @Override
    public Url persistShortLink(Url url) {
        return null;
    }

    @Override
    public Url getEncodedUrl(String url) {
        return null;
    }

    @Override
    public void deleteShortLink(Url url) {

    }
}
