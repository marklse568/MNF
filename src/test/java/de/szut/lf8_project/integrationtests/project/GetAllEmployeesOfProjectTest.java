package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllEmployeesOfProjectTest extends BaseIntegrationTest {
    @Test
    void findAllProjects() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(9);
        employee = this.employeeRepository.saveAndFlush(employee);

        LocalDate startDate = LocalDate.now().minusMonths(11);
        LocalDate endDate = LocalDate.now().plusYears(4);
        LocalDate plannedEndDate = LocalDate.now().plusYears(2);

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

        this.mockMvc.perform(get("/project/{projectId}/employees", project.getId())
                        .header("Authorization", generateJwt()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is((int) project.getId())))
                .andExpect(jsonPath("$.name", is(project.getName())))
                .andExpect(jsonPath("$.employees[0].id", is((int) employee.getId())))
                .andExpect(jsonPath("$.employees[0].qualification", is(link.getQualification())))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
