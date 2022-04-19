package com.datastaxdev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastaxdev.todo.cassandra.TodoServiceCassandraCql;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceReadyEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * Execute some Action and application startup.
 *
 * @author Cedrick LUNVEN (@clunven)
 */
@Singleton
public class TodoApplicationStartup  implements ApplicationEventListener<ServiceReadyEvent> {

    /** Logger for the class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoApplicationStartup.class);
    
    @Property(name = "todo.cassandra.create_schema", defaultValue="false")
    private boolean createTable;
    
    @Inject 
    private CqlSession cqlSession;
    
    /** {@inheritDoc} */
    @Override
    public void onApplicationEvent(final ServiceReadyEvent event) {
        LOGGER.info("Startup Initialization");
        if (createTable) {
            TodoServiceCassandraCql.createTableTodo(cqlSession);
            LOGGER.info("+ Table TodoItems created if needed.");
        }
        LOGGER.info("[OK]");
        
        /* Accessing Context
        try (ApplicationContext context = ApplicationContext.run()) {
            cqlSession = context.getBean(CqlSession.class);
        }
        */
        
    }


}




