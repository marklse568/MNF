package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FindProjectByIdTest extends BaseIntegrationTest {
    @Test
    void findProjectById() throws Exception {
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

        this.mockMvc.perform(get("/project/" + projectId)
                        .header("Authorization", generateJwt()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is((int) projectId)))
                .andExpect(jsonPath("$.responsibleEmployeeId", is((int) project.getResponsibleEmployee().getId())))
                .andExpect(jsonPath("$.clientId", is((int) project.getClientId())))
                .andExpect(jsonPath("$.clientContactPersonInfo", is(project.getClientContactPersonInfo())))
                .andExpect(jsonPath("$.comment", is(project.getComment())))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
}

