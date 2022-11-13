package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        LocalDate startDate = LocalDate.now().minusMonths(11);
        LocalDate endDate = LocalDate.now().plusYears(4);
        LocalDate plannedEndDate = LocalDate.now().plusYears(2);

        LocalDate updatedStartDate = LocalDate.now().minusMonths(10);
        LocalDate updatedEndDate = LocalDate.now().plusYears(3);
        LocalDate updatedPlannedEndDate = LocalDate.now().plusYears(1);

        String testStartDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(updatedStartDate);
        String testEndDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(updatedEndDate);
        String testPlannedEndDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(updatedPlannedEndDate);

        ProjectEntity project = new ProjectEntity();
        project.setResponsibleEmployee(employee);
        project.setClientId(456);
        project.setClientContactPersonInfo("Test123");
        project.setComment("wat");
        project.setName("Testprojekt");
        project.setStartDate(startDate);
        project.setPlannedEndDate(plannedEndDate);
        project.setEndDate(endDate);
        project = this.projectRepository.saveAndFlush(project);

        String content = String.format("{\n" +
                "\t\"id\": %d,\n" +
                "\t\"clientId\": 789,\n" +
                "\t\"clientContactPersonInfo\": \"Test456\",\n" +
                "\t\"name\": \"Anders\",\n" +
                "\t\"comment\": \"updated successfully\",\n" +
                "\t\"startDate\": \"" + testStartDate + "\",\n" +
                "\t\"endDate\": \"" + testEndDate + "\",\n" +
                "\t\"plannedEndDate\": \"" + testPlannedEndDate + "\"\n" +
                "}", project.getId());

        this.mockMvc.perform(
                        put("/project").content(content).contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", generateJwt()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id", is((int) project.getId())))
                .andExpect(jsonPath("responsibleEmployeeId", is((int) employee.getId())))
                .andExpect(jsonPath("clientId", is(789)))
                .andExpect(jsonPath("name", is("Anders")))
                .andExpect(jsonPath("clientContactPersonInfo", is("Test456")))
                .andExpect(jsonPath("comment", is("updated successfully")))
                .andExpect(jsonPath("startDate", is(testStartDate)))
                .andExpect(jsonPath("endDate", is(testEndDate)))
                .andExpect(jsonPath("plannedEndDate", is(testPlannedEndDate)))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
