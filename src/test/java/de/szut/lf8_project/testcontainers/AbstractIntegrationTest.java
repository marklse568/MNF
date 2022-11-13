package de.szut.lf8_project.testcontainers;

import de.szut.lf8_project.employee.EmployeeRepository;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectRepository;
import de.szut.lf8_project.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * A fast slice test will only start jpa context.
 * <p>
 * To use other context beans use org.springframework.context.annotation.@Import annotation.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
@ContextConfiguration(initializers = PostgresContextInitializer.class)
public class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected EmployeeProjectRepository employeeProjectRepository;

    @BeforeEach
    void setUp() {
        projectRepository.deleteAll();
        employeeRepository.deleteAll();
    }
}
