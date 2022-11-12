package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FindAllProjectsTest extends BaseIntegrationTest {
    @Test
    void findAllProjects() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(9);
        employee = this.employeeRepository.saveAndFlush(employee);

        ProjectEntity project = new ProjectEntity();
        project.setResponsibleEmployee(employee);
        project.setClientId(456);
        project.setClientContactPersonInfo("Test123");
        project.setComment("to the moon");
        project = this.projectRepository.saveAndFlush(project);

        long projectId = project.getId();

        this.mockMvc.perform(get("/project")
                        .header("Authorization", generateJwt()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id", is((int) projectId)))
                .andExpect(jsonPath("$.[0].responsibleEmployeeId", is((int) project.getResponsibleEmployee().getId())))
                .andExpect(jsonPath("$.[0].clientId", is((int) project.getClientId())))
                .andExpect(jsonPath("$.[0].clientContactPersonInfo", is(project.getClientContactPersonInfo())))
                .andExpect(jsonPath("$.[0].comment", is(project.getComment())))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
}
