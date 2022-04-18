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
public class CqlSessionProvider {
    
    /** Logger for the class. */
    static Logger LOGGER = LoggerFactory.getLogger(CqlSessionProvider.class);
   
    public static final String KEYSPACE           = "devoxx";
    public static final String LOCAL_DATACENTER   = "dc1";
    public static final String CONTACT_POINT      = "localhost";
    public static final int    CONTACT_POINT_PORT = 9042;
    public static final String ASTRA_USERNAME     = "token";
    public static final String ASTRA_PASSWORD     = System.getProperty("token");
    //public static final String ASTRA_BUNDLE       = "/home/gitpod/.cassandra/bootstrap.zip";
    public static final String ASTRA_BUNDLE       = "/Users/cedricklunven/Downloads/secure-connect-workshops.zip";
    
    private static CqlSessionProvider _instance;
    
    /** Singleton we would like to use everywhere. */
    private CqlSession cqlSession;
    
    /**
     * Initialization of CqlSession
     */
    private CqlSessionProvider() {
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
    protected static synchronized CqlSessionProvider getInstance() {
        if (_instance == null) {
            _instance = new CqlSessionProvider();
        }
        return _instance;
    }
    
    protected static CqlSession connectToLocalCassandra() {
        LOGGER.info("+ Connecting to [LOCAL CASSANDRA]");
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(CONTACT_POINT, CONTACT_POINT_PORT))
                .withLocalDatacenter(LOCAL_DATACENTER)
                .withKeyspace(KEYSPACE)
                .build();
    }
    
    protected static CqlSession connectoToAstra() {
        LOGGER.info("+ Connecting to [ASTRA]");
        return CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(ASTRA_BUNDLE))
                .withAuthCredentials(ASTRA_USERNAME, ASTRA_PASSWORD)
                .withKeyspace(KEYSPACE)
                .build();
    }
    

}
