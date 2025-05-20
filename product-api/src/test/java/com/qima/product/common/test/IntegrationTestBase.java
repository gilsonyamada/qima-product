package com.qima.product.common.test;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class IntegrationTestBase {
    
    protected IntegrationTestBase() {
        // Prevent instantiation
    }
    
    @BeforeAll
    static void startContainer() {
        PostgreSQLTestContainer.getInstance();
    }

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        PostgreSQLTestContainer container = PostgreSQLTestContainer.getInstance();
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}
