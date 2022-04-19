package com.datastaxdev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
public class HelloControllerTest2 {

    @Inject
    @Client("/")
    HttpClient client;
    
    @Test
    public void testHelloWorldResponse() {
        HttpRequest<String> request = HttpRequest.GET("/hello");  
        String response = client.toBlocking().retrieve(request); 
        assertEquals("Hello World", response);
    }
    
}
