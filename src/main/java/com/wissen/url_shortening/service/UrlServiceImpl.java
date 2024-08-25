package com.wissen.url_shortening.service;

import com.google.common.hash.Hashing;
import com.wissen.url_shortening.model.Url;
import com.wissen.url_shortening.model.UrlDto;
import com.wissen.url_shortening.repository.UrlRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class UrlServiceImpl implements UrlService {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url generateShortLink(UrlDto urlDto) {
        String originalUrl = urlDto.getUrl();
        String shortUrl = "";
        if (StringUtils.isNotEmpty(originalUrl)) {
            // Generate a short URL
            shortUrl = generateShortUrl(originalUrl);

            //persist shortUrl to the database
            Url urlToPersist = new Url();
            urlToPersist.setCreationDate(LocalDateTime.now());
            urlToPersist.setOriginalUrl(urlDto.getUrl());
            urlToPersist.setShortLink(shortUrl);
            urlToPersist.setExpirationDate(getExpirationDateUtil(urlDto.getExpirationDate(),
                    urlToPersist.getCreationDate()));
            Url urlToReturn = persistShortLink(urlToPersist);

            if (urlToReturn != null) {
                return urlToReturn;
            }
        }
        return null;
    }

    private LocalDateTime getExpirationDateUtil(String expiratoinDate, LocalDateTime creationDate) {
        //isBlank(): Check if the String is null or has only whitespaces. Modified
        //from org. apache. commons. lang. StringUtils#isBlank(String)
        if (StringUtils.isBlank(expiratoinDate)) {
            //set expiry for 1 minute for new url request, if expiration date is not given
            return creationDate.plusSeconds(60);
        }
        //Obtains an instance of LocalDateTime from a text string such as 2007-12-03T10:15:30.
        LocalDateTime expirationDateToReturn = LocalDateTime.parse(expiratoinDate);
        return expirationDateToReturn;
    }

    /*Since MD5 produces a large hash and we're truncating it to 7 characters,
     there's a small chance of collisions (different URLs generating the same short URL).
     This can be mitigated by checking for collisions or by using a different hash
     function with a larger bit size.
    using md5() algorithm & writing custom base62Encoded() method logic*/
    private String generateShortUrl(String originalUrl) {
        // Add a unique salt or timestamp to make url unique
        String saltedUrl = originalUrl + LocalDateTime.now().toString();
        //Generate 128-bit hash value (32 hexadecimal characters) from the original URL
        // using MD5 algorithm
        //32 characters in hash string
        String hash = Hashing.md5().hashString(saltedUrl, StandardCharsets.UTF_8).toString();
        //32 hexadecimal characters has been generated
        System.out.println("String hash due to md5() => " + hash);
        //convert hash to BigInteger to handle large numbers
        BigInteger hashNumber = new BigInteger(hash, 16);
        //40 digits Big integer
        System.out.println("Hash number due to md5() => " + hashNumber);

        //Base62 encoding of the hash (custom method)
        String base62Encoded = base62Encoded(hashNumber);

        //take the first 7 characters
        return base62Encoded.substring(0, 7);
    }

    private String base62Encoded(BigInteger hashNumber) {
        StringBuilder encodedString = new StringBuilder();

        while (hashNumber.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] quotientAndRemainder = hashNumber.divideAndRemainder(BigInteger.valueOf(62));
            // quotientAndRemainder[0] =  hashNumber/62;
            // quotientAndRemainder[1] = hashNumber%62;
            encodedString.append(BASE62.charAt(quotientAndRemainder[1].intValue()));
            hashNumber = quotientAndRemainder[0];
        }
        //reverse the string as encoding produces the least significant digit first(LSB)
        return encodedString.reverse().toString();
    }

    //save url to the database
    @Override
    public Url persistShortLink(Url url) {
        Url urlToReturn = urlRepository.save(url);
        return urlToReturn;
    }

    //get original url from short url
    @Override
    public Url getEncodedUrl(String url) {
        Url urlToReturn = urlRepository.findByShortLink(url);
        return urlToReturn;
    }

    //delete for key-value<short_   url, original_url> pair
    @Override
    public void deleteShortLink(Url url) {
        urlRepository.delete(url);
    }
}
