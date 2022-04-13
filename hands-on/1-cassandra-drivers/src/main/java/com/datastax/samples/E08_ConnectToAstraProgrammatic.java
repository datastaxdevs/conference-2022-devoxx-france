package com.datastax.samples;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.samples.schema.SchemaConstants;

/**
 * This class shows how to connect to the DataStax Cloud Cassandra As a Service: ASTRA
 * 
 *  Pre-requisites:
 * ===================
 * 
 * 1. You need an ASTRA intance : go to astra.datastax.com and create an instance there. There is a free tier
 * for you to have a 3-node clusters availables forever. You can find more info on:
 *
 * 2. You need to provide you ASTRA credentials username, password, keyspace 
 * but also the secure bundle ZIP. To download it please follow the instruction on : 
 * https://docs.datastax.com/en/developer/java-driver/4.5/manual/cloud/
 * 
 * 3. You need a java driver version 3.8
 */
public class E08_ConnectToAstraProgrammatic implements SchemaConstants {
    
    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger(E08_ConnectToAstraProgrammatic.class);
    
    /** StandAlone (vs JUNIT) to help you running. */
    public static void main(String[] args) {
        
        // ----
        // #1. Connecting explicitely using the CqlSessionBuilder
        // ----
        
        // Those are mandatory to connect to ASTRA
        final String ASTRA_ZIP_FILE     = "/Users/cedricklunven/Downloads/secure-connect-javazone.zip";
        final String ASTRA_KEYSPACE     = "javazone";
        final String ASTRA_CLIENTID     = "LorYCpiKoBGtPZvTaXkJYUvf";
        final String ASTRA_CLIENTSECRET = "PDRdeNf8Hb0+KTipmev1nPiYQuXbpMpW1wuN.ywz1.UtKRnnJQzgLRZu4cExSGc2xHIQHREzpeB,pugXl,vlSXStTpNxjhPBnl0yZjXJRyFIg2ZJ-K8SZZWHIcdH0SzS";
        
        // Check the cloud zip file
        File cloudSecureConnectBundleFile = new File(ASTRA_ZIP_FILE);
        if (!cloudSecureConnectBundleFile.exists()) {
            throw new IllegalStateException("File '" + ASTRA_ZIP_FILE + "' has not been found\n"
                    + "To run this sample you need to download the secure bundle file from ASTRA WebPage\n"
                    + "More info here:");
        }
        
        // Connect
        try (CqlSession cqlSession = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get(ASTRA_ZIP_FILE))
                .withAuthCredentials(ASTRA_CLIENTID, ASTRA_CLIENTSECRET)
                .withKeyspace(ASTRA_KEYSPACE)
                .build()) {
            LOGGER.info("[OK] Welcome to ASTRA. Connected to Keyspace {}", cqlSession.getKeyspace().get());
        }
        LOGGER.info("[OK] Success");
        System.exit(0);
    }
    
}
