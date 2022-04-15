package com.datastax.lab4;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.Test;

import com.datastax.oss.driver.api.core.CqlSession;

public class Test_01_Connect {
    
    @Test
    public void test() {
        try (CqlSession cqlSession = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("dc1")
                .build()) {
            System.out.println(cqlSession.execute("select data_center from system.local").one().getString("data_center"));
        }
    }

}
