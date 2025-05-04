package com.praktiker.shop.utilis.seeders;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class TestDataInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        TestDataSeeder dataSeeder = context.getBean(TestDataSeeder.class);
        dataSeeder.initializeDatabase();
    }
}
