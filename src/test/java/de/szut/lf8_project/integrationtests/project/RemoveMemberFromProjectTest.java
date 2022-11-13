package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class RemoveMemberFromProjectTest extends BaseIntegrationTest {
    @Test
    void removeMemberFromProject() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(9);
        employee = this.employeeRepository.saveAndFlush(employee);

        ProjectEntity project = new ProjectEntity();
        project.setResponsibleEmployee(employee);
        project.setClientId(456);
        project.setClientContactPersonInfo("Test123");
        project.setComment("to the moon");
        project = this.projectRepository.saveAndFlush(project);

        EmployeeProjectEntity link = new EmployeeProjectEntity();
        link.setProject(project);
        link.setEmployee(employee);
        link.setQualification("Sudoku");
        this.employeeProjectRepository.saveAndFlush(link);

        /*
         The following code will solely check for the request status not being any server error status
         since we can't guarantee the availability/integrity of the chosen data in the external Employee API
         nor the availability of the API itself
        */
        this.mockMvc.perform(delete("/project/{projectId}/employees/{employeeId}", project.getId(), employee.getId())
                        .header("Authorization", generateJwt()))
                .andExpect(mvcResult -> assertThat(mvcResult.getResponse().getStatus()).isLessThan(500))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
