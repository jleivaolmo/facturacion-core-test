package com.echevarne.sap.cloud.facturacion.config;

import java.sql.SQLException;

import javax.annotation.PostConstruct;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class DatabaseInit {

    

    /**
     * DriverName: 2:Oracle JDBC driver; 3:H2 JDBC Driver
     *
     * @throws SQLException
     */
    @PostConstruct
    public void initData() throws Exception {
        // populator.runForTest();
    }

}
