package com.datastax.samples;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
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
public class E09_ConnectToAstraConfFile implements SchemaConstants {
    
    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger(E09_ConnectToAstraConfFile.class);
    
    /** StandAlone (vs JUNIT) to help you running. */
    public static void main(String[] args) {
        String configFile = E09_ConnectToAstraConfFile.class.getResource("/custom_astra.conf").getFile();
        DriverConfigLoader configLoader = DriverConfigLoader.fromFile(new File(configFile));
        try (CqlSession cqlSession = CqlSession.builder().withConfigLoader(configLoader).build()) {
            // Use session
            LOGGER.info("[OK] Welcome to ASTRA (conf file). Connected to Keyspace {}", 
                    cqlSession.getKeyspace().get());
        }
        LOGGER.info("[OK] Success");
        System.exit(0);
    }
    
}
