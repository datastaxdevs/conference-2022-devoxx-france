package com.datastax.samples;

import java.net.InetSocketAddress;
import java.nio.file.Paths;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * This me
 * You can run the LABS locally, in gitpod with Docker or with Astra.
 * 
 */
public class CqlSessionLabsProvider {
    
    /** Singleton we would like to use everywhere. */
    private static CqlSession cqlSession;
    
    protected static synchronized CqlSession getCqlSession() {
        if (cqlSession == null) {
            //cqlSession = connectToLocalCassandra();
            cqlSession = connectoToAstraCassandra();
        }
        return cqlSession;
    }
    
    protected static CqlSession connectToLocalCassandra() {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("dc1")
                .build();
    }
    
    protected static CqlSession connectoToAstraCassandra() {
        // Values for Gitpod
        final String keyspace            = "devoxx";
        final String username            = "token";
        //final String secureConnectBundle = "/home/gitpod/.cassandra/bootstrap.zip";
        //final String password            = System.getenv("ASTRA_DB_APPLICATION_TOKEN");
        
        // Values for my machine
        final String secureConnectBundle = "/Users/cedricklunven/Downloads/secure-connect-workshops.zip";
        final String password            = "AstraCS:gdZaqzmFZszaBTOlLgeecuPs:edd25600df1c01506f5388340f138f277cece2c93cb70f4b5fa386490daa5d44";
        
        return CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(secureConnectBundle))
                .withAuthCredentials(username, password)
                .withKeyspace(keyspace)
                .build();
    }
    
    protected static void closeSession() {
        cqlSession.close();
    }

}
