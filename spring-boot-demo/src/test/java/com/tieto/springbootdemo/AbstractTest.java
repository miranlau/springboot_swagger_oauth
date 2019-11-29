package com.tieto.springbootdemo;

import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

public class AbstractTest {
    public static DB db;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBConfigurationBuilder configBuilder = DBConfigurationBuilder.newBuilder();
        configBuilder.setPort(7777);
        configBuilder.addArg("--user=root");

        db = DB.newEmbeddedDB(configBuilder.build());
        db.start();

        db.source("schema/oam.alarm.sql", null, null, "test");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        db.stop();
    }

    //@Test
    public void keepDB() throws Exception {
        System.in.read();
    }
}
