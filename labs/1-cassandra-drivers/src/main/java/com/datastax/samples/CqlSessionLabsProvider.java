package com.datastax.samples;

import java.net.InetSocketAddress;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * This me
 * You can run the LABS locally, in gitpod with Docker or with Astra.
 * 
 */
public class CqlSessionLabsProvider {
    
    /** Logger for the class. */
    static Logger LOGGER = LoggerFactory.getLogger(CqlSessionLabsProvider.class);
   
    private static CqlSessionLabsProvider _instance;
    
    /** Singleton we would like to use everywhere. */
    private CqlSession cqlSession;
    
    /**
     * Initialization of CqlSession
     */
    private CqlSessionLabsProvider() {
        LOGGER.info("Creating your CqlSession to Cassandra...");
        cqlSession = connectToLocalCassandra();
        //cqlSession = connectoToAstra();
        LOGGER.info("+ [OK] Your are connected.");
    }
    
    /**
     * Getter for cqlSession.
     *
     * @return cqlSession
     */
    public CqlSession getSession() {
        return cqlSession;
    }
    
    /**
     * Create a CqlSession.
     *
     * @return
     */
    protected static synchronized CqlSessionLabsProvider getInstance() {
        if (_instance == null) {
            _instance = new CqlSessionLabsProvider();
        }
        return _instance;
    }
    
    protected static CqlSession connectToLocalCassandra() {
        LOGGER.info("+ Connecting to [LOCAL CASSANDRA]");
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("dc1")
                .build();
    }
    
    protected static CqlSession connectoToAstra() {
        LOGGER.info("+ Connecting to [ASTRA]");
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
    

}
