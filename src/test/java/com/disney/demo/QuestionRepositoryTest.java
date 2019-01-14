package com.disney.demo;

import com.disney.demo.model.Question;
import com.disney.demo.repository.QuestionRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(initializers = {QuestionRepositoryTest.Initializer.class})
public class QuestionRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer =
        (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withStartupTimeout(Duration.ofSeconds(10));

    @Autowired
    private QuestionRepository questionRepo;

    @Test
    public void testWithDb() {

        Question q1 = new Question("title1", "text1");
        Question q2 = new Question("title2", "text2");
        Question q3 = new Question("title3", "text3");

        Question q1saved = questionRepo.save(q1);
        Question q2saved = questionRepo.save(q2);
        Question q3saved = questionRepo.save(q3);

        assertThat(q1saved)
            .matches(q -> q.getId() != null)
            .matches(q -> q.getTitle().equals("title1"))
            .matches(q -> q.getText().equals("text1"));
        assertThat(q2saved)
            .matches(q -> q.getId() != null)
            .matches(q -> q.getTitle().equals("title2"))
            .matches(q -> q.getText().equals("text2"));
        assertThat(q3saved)
            .matches(q -> q.getId() != null)
            .matches(q -> q.getTitle().equals("title3"))
            .matches(q -> q.getText().equals("text3"));

        assertThat(questionRepo.findAll()).containsExactly(q1, q2, q3);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(@NotNull ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(context.getEnvironment());
        }
    }
}