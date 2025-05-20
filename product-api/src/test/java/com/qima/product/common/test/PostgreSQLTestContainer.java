package com.qima.product.common.test;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLTestContainer extends PostgreSQLContainer<PostgreSQLTestContainer> {

    private static final String IMAGE_VERSION = "postgres:16";
    private static PostgreSQLTestContainer container;

    private PostgreSQLTestContainer() {
        super(IMAGE_VERSION);
        withDatabaseName("qima");
        withUsername("qima_user");
        withPassword("qima_pass");
    }

    public static PostgreSQLTestContainer getInstance() {
        if (container == null) {
            container = new PostgreSQLTestContainer();
            container.start();
        }
        return container;
    }

    @Override
    public void stop() {
        // Do nothing, JVM will handle shutdown
    }

}
