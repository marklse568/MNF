package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateProjectTest extends BaseIntegrationTest {
    @Test
    void createProject() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(9);
        employee = this.employeeRepository.saveAndFlush(employee);

        String content = String.format("{\n" +
                "\t\"responsibleEmployeeId\": %d,\n" +
                "\t\"clientId\": 789,\n" +
                "\t\"clientContactPersonInfo\": \"Testperson\",\n" +
                "\t\"name\": \"Anders\",\n" +
                "\t\"comment\": \"updated successfully\"\n" +
                "}", employee.getId());

        /*
         The following code will solely check for the request status not being any server error status
         since we can't guarantee the availability/integrity of the chosen data in the external Employee API
         nor the availability of the API itself.
         This is needed since the ID of the responsible employee will be checked against the Employee API
        */
        this.mockMvc.perform(put("/project")
                        .content(content).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", generateJwt()))
                .andExpect(mvcResult -> assertThat(mvcResult.getResponse().getStatus()).isLessThan(500))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
