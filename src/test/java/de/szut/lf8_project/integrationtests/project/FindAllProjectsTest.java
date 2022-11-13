package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

        LocalDate startDate = LocalDate.now().minusMonths(11);
        LocalDate endDate = LocalDate.now().plusYears(4);
        LocalDate plannedEndDate = LocalDate.now().plusYears(2);

        String expectedStartDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(startDate);
        String expectedEndDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(endDate);
        String expectedPlannedEndDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(plannedEndDate);

        ProjectEntity project = new ProjectEntity();
        project.setResponsibleEmployee(employee);
        project.setClientId(456);
        project.setClientContactPersonInfo("Test123");
        project.setComment("to the moon");
        project.setName("TestProject");
        project.setStartDate(startDate);
        project.setPlannedEndDate(plannedEndDate);
        project.setEndDate(endDate);
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
                .andExpect(jsonPath("$.[0].name", is(project.getName())))
                .andExpect(jsonPath("$.[0].startDate", is(expectedStartDate)))
                .andExpect(jsonPath("$.[0].plannedEndDate", is(expectedPlannedEndDate)))
                .andExpect(jsonPath("$.[0].endDate", is(expectedEndDate)))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
}
