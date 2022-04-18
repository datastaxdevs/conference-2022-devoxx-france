package com.datastax.samples;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.samples.schema.SchemaConstants;

/**
 * Sample code to create tables, types and objects in a keyspace.
 * 
 * Pre-requisites:
 * - Cassandra running locally (127.0.0.1, port 9042)
 * - Keyspace killrvideo created {@link E01_CreateKeyspace}
 * 
 * @author Cedrick LUNVEN (@clunven)
 */
public class E04_ConfigurationFile implements SchemaConstants {
    
    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger(E04_ConfigurationFile.class);
    
    /** StandAlone (vs JUNIT) to help you running. */
    public static void main(String[] args) {
        
        // Load Configuration from file
        String confFilePath = E04_ConfigurationFile.class.getResource("/custom_application.conf").getFile();
        
        // Create a Load with this file
        DriverConfigLoader loader = DriverConfigLoader.fromFile(new File(confFilePath));
        
        // Use it to create the session
        try (CqlSession cqlSession = CqlSession.builder().withConfigLoader(loader).build()) {
            
            // Use session
            LOGGER.info("[OK] Connected to Keyspace {}", cqlSession.getKeyspace().get());
        }
        LOGGER.info("[OK] Success");
        System.exit(0);
    }
    
}
