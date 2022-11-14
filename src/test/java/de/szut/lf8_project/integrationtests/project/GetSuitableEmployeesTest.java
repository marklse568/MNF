package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetSuitableEmployeesTest extends BaseIntegrationTest {
    @Test
    void findAllSuitable() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(9);
        employee = this.employeeRepository.saveAndFlush(employee);

        EmployeeEntity responsibleEmployee = new EmployeeEntity();
        responsibleEmployee.setId(10);
        responsibleEmployee = this.employeeRepository.saveAndFlush(responsibleEmployee);

        LocalDate startDate = LocalDate.now().minusMonths(11);
        LocalDate endDate = LocalDate.now().plusYears(4);
        LocalDate plannedEndDate = LocalDate.now().plusYears(2);

        LocalDate laterStartDate = LocalDate.now().plusYears(11);
        LocalDate laterPlannedEndDate = LocalDate.now().plusYears(12);

        ProjectEntity projectWithEmployee = new ProjectEntity();
        projectWithEmployee.setResponsibleEmployee(responsibleEmployee);
        projectWithEmployee.setClientId(456);
        projectWithEmployee.setClientContactPersonInfo("Test123");
        projectWithEmployee.setComment("to the moon");
        projectWithEmployee.setName("TestProject");
        projectWithEmployee.setStartDate(startDate);
        projectWithEmployee.setPlannedEndDate(plannedEndDate);
        projectWithEmployee.setEndDate(endDate);
        projectWithEmployee = this.projectRepository.saveAndFlush(projectWithEmployee);

        ProjectEntity requestedProjectWithoutEmployee = new ProjectEntity();
        requestedProjectWithoutEmployee.setResponsibleEmployee(employee);
        requestedProjectWithoutEmployee.setClientId(458);
        requestedProjectWithoutEmployee.setClientContactPersonInfo("Test346");
        requestedProjectWithoutEmployee.setComment("to the death");
        requestedProjectWithoutEmployee.setName("TestProject 2 - Electric Boogaloo");
        requestedProjectWithoutEmployee.setStartDate(laterStartDate);
        requestedProjectWithoutEmployee.setPlannedEndDate(laterPlannedEndDate);
        requestedProjectWithoutEmployee = this.projectRepository.saveAndFlush(requestedProjectWithoutEmployee);

        EmployeeProjectEntity link = new EmployeeProjectEntity();
        link.setProject(projectWithEmployee);
        link.setEmployee(employee);
        link.setQualification("Sudoku");
        this.employeeProjectRepository.saveAndFlush(link);

        String content = String.format("{\n" +
                "\t\"qualification\": \"%s\"\n" +
                "}", link.getQualification());

        this.mockMvc.perform(get("/project/{projectId}/employees/suitable", requestedProjectWithoutEmployee.getId())
                        .content(content).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", generateJwt()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].qualification", is(link.getQualification())))
                .andExpect(jsonPath("$.[0].id", is((int) employee.getId())))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
