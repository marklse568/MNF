package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateProjectTest extends BaseIntegrationTest {
    @Test
    void updateProject() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(9);
        employee = this.employeeRepository.saveAndFlush(employee);

        ProjectEntity project = new ProjectEntity();
        project.setResponsibleEmployee(employee);
        project.setClientId(456);
        project.setClientContactPersonInfo("Test123");
        project.setComment("updated successfully");
        project = this.projectRepository.saveAndFlush(project);

        String content = String.format("{\n" +
                "\t\"id\": %d,\n" +
                "\t\"responsibleEmployeeId\": %d,\n" +
                "\t\"clientId\": 456,\n" +
                "\t\"clientContactPersonInfo\": \"Test123\",\n" +
                "\t\"comment\": \"updated successfully\"\n" +
                "}", project.getId(), employee.getId());

        this.mockMvc.perform(put("/project").content(content).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", generateJwt()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id", is((int) project.getId())))
                .andExpect(jsonPath("responsibleEmployeeId", is("Test123")))
                .andExpect(jsonPath("clientId", is(456)))
                .andExpect(jsonPath("clientContactPersonInfo", is("Test123")))
                .andExpect(jsonPath("comment", is("updated successfully")))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
