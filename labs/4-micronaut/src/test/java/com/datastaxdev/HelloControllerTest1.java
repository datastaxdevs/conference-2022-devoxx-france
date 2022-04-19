package com.datastaxdev;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;

public class HelloControllerTest1 {

    private static EmbeddedServer server; 

    private static HttpClient client;
    
    @BeforeAll 
    public static void setupServer() {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server.getApplicationContext().createBean(HttpClient.class, server.getURL());
    }

    @AfterAll 
    public static void stopServer() {
        if (server != null) {
            server.stop();
         }
         if (client != null) {
            client.stop();
        }
    }

    @Test
    public void testHelloWorldResponse() {
        String response = client.toBlocking().retrieve("/hello"); 
        assertEquals("Hello World", response);
    }
    
}
