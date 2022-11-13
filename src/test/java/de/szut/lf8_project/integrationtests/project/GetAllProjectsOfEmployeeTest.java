package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllProjectsOfEmployeeTest extends BaseIntegrationTest {
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

        EmployeeProjectEntity link = new EmployeeProjectEntity();
        link.setProject(project);
        link.setEmployee(employee);
        link.setQualification("Sudoku");
        this.employeeProjectRepository.saveAndFlush(link);

        this.mockMvc.perform(get("/employee/" + employee.getId() + "/projects")
                        .header("Authorization", generateJwt()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is((int) employee.getId())))
                .andExpect(jsonPath("$.projects[0].id", is((int) project.getId())))
                .andExpect(jsonPath("$.projects[0].name", is(project.getName())))
                .andExpect(jsonPath("$.projects[0].startDate", is(expectedStartDate)))
                .andExpect(jsonPath("$.projects[0].plannedEndDate", is(expectedPlannedEndDate)))
                .andExpect(jsonPath("$.projects[0].endDate", is(expectedEndDate)))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
