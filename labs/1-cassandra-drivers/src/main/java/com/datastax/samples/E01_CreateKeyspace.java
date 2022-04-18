package com.datastax.samples;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.samples.schema.SchemaConstants;

public class E01_CreateKeyspace implements SchemaConstants {
    
    private static Logger LOGGER = LoggerFactory.getLogger(E01_CreateKeyspace.class);
    
    public static void main(String[] args) {
        try(CqlSession cqlSession = CqlSessionProvider.getInstance().getSession()) {
            
            SimpleStatement createKeyspace = SchemaBuilder
                .createKeyspace(KEYSPACE_NAME)
                .ifNotExists()
                .withNetworkTopologyStrategy(Map.of(CqlSessionProvider.LOCAL_DATACENTER, 3))
                .withDurableWrites(true)
                .build();
            cqlSession.execute(createKeyspace);
            LOGGER.info("Keyspace '{}' created (if needed).", KEYSPACE_NAME);
        }
    }
}
