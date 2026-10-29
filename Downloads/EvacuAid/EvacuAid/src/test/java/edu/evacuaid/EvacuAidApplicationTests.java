package edu.evacuaid;

import org.junit.jupiter.api.Test; import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(properties="spring.datasource.url=jdbc:h2:mem:evacuaid-context-test;DB_CLOSE_DELAY=-1") class EvacuAidApplicationTests { @Test void contextLoads(){} }
