package com.spomprt.weightminder;

import com.spomprt.weightminder.repository.PersonRepository;
import com.spomprt.weightminder.repository.RecordRepository;
import com.spomprt.weightminder.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AbstractSpringBootTest.Config.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AbstractSpringBootTest {

    @Autowired
    protected PersonRepository personRepository;

    @Autowired
    protected RecordRepository recordRepository;

    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15.0")
    );

    static {
        postgresContainer.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.jpa.show-sql", () -> false);
    }

    @BeforeEach
    void beforeEach() {
        personRepository.deleteAll();
        recordRepository.deleteAll();
        log.info("*** Clean database ***");
    }

    @TestConfiguration
    static class Config {

        @Bean
        public PersonService personService(
                PersonRepository personRepository
        ) {
            return new PersonService(personRepository);
        }

    }
}
