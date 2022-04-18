package com.datastax.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.samples.schema.SchemaConstants;
import com.datastax.samples.schema.SchemaUtils;

/**
 * Create a keyspace with Simple Strategy and replication factor 1 (for local environment)
 * 
 * Pre-requisites:
 * - Cassandra running at (ip=127.0.0.1, port=9042, datacenter=datacenter1)
 * 
 * This code below will execute the following CQL statement:
 * --------------------------------------------------------------------
 * CREATE KEYSPACE killrvideo 
 * WITH replication = 
 *      {'class': 'SimpleStrategy', 
 *       'replication_factor': '1'}  
 * AND durable_writes = true;
 * ---------------------------------------------------------------------
 * 
 * @author DataStax Developer Advocate Team
 * 
 * Need Help ? Join us on community.datastax.com to ask your questions for free.
 */
public class E01_CreateKeyspace implements SchemaConstants {
    
    /** Logger for the class. */
    private static Logger LOGGER = LoggerFactory.getLogger(E01_CreateKeyspace.class);
    
    /** 
     * StandAlone program relying on main method to easy copy/paste.
     */
    public static void main(String[] args) {
        LOGGER.info("Creating Keyspace 'killrvideo'");
        SchemaUtils.createKeyspace();
        System.exit(0);
    }
     
}
