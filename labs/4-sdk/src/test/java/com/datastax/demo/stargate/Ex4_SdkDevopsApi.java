package com.datastax.demo.stargate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.datastax.astra.sdk.AstraClient;
import com.datastax.astra.sdk.databases.domain.DatabaseFilter;
import com.datastax.astra.sdk.databases.domain.DatabaseFilter.Include;

@SpringBootTest
public class Ex4_SdkDevopsApi {

    @Autowired
    private AstraClient astraClient;
    
    @Test
    public void listAvailableDatabases() {
        astraClient.apiDevopsDatabases()
                   .searchDatabases(DatabaseFilter
                            .builder()
                            .include(Include.ACTIVE)
                            .build())
                   .forEach(db -> {
          System.out.println("Database '" + db.getInfo().getName() + "'");
          System.out.println("+ id=" + db.getId());
          System.out.println("+ region=" + db.getInfo().getRegion());
          System.out.println("+ keyspace=" + db.getInfo().getKeyspace());
          System.out.println("");
        });
    }
    
    
    
    
    
}
