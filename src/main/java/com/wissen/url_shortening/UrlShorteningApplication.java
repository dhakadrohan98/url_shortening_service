package com.wissen.url_shortening;

import com.wissen.url_shortening.pojo.UrlShorteningRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableDiscoveryClient
public class UrlShorteningApplication {

//    @Autowired
//    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(UrlShorteningApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        //create a thread pool executor with fixed no. of threads
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(400); //no. of threads to use
//        taskExecutor.setMaxPoolSize(500); //max threads
//        taskExecutor.setQueueCapacity(1000); //queue capacity for tasks
//        taskExecutor.initialize();
//
//        String baseUrl = "http://localhost:8081/generate"; //API url
//
//        for (int i = 1000; i < 2000; i++) {
//            Thread.sleep(50); // wait for 50 ms
//            final int index = i;
//            taskExecutor.submit(() -> {
//                String originalUrl = "https://github.com/dhakadrohan98/morgan_stanley/blob/dsa/Goldman_Sachs/src/com/wissen/DP/AllSubsequencesOfString.java";
//                String expirationDate = "2024-09-30T23:10:00";
//                try {
//                    String shortUrl = callUrlShorteningApi(baseUrl, originalUrl, expirationDate);
//                    // Log or save the response if necessary
//                    System.out.println("Shortened URL: " + shortUrl);
//                } catch (Exception e) {
//                    System.err.println("Failed to shorten URL: " + e.getMessage());
//                }
//            });
//        }
//        // Shutdown the executor after all tasks are completed
//        taskExecutor.shutdown();
//        taskExecutor.getThreadPoolExecutor().awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//    }
//
//    private String callUrlShorteningApi(String baseUrl, String originalUrl, String expirationDate) {
//        // create a new UrlShorteningRequest object for each request
//        UrlShorteningRequest urlShorteningRequest = new UrlShorteningRequest(originalUrl, expirationDate);
//
//        //set the request headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        //create the HttpEntity object with the request body & headers
//        HttpEntity<UrlShorteningRequest> entity = new HttpEntity<>(urlShorteningRequest, headers);
//
//        // Make the POST request to the API
//        return restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class).getBody();
//    }
}
