package com.datastax.samples;

import com.datastax.oss.driver.api.core.CqlSession;

public class E00_TestConnecvity {
    
    public static void main(String[] args) {
        try(CqlSession cqlSession = CqlSessionLabsProvider.getCqlSession()) {
            System.out.println(cqlSession.execute("select data_center from system.local")
                      .one()
                      .getString("data_center"));
        }
            
    }
    

}
