package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteProjectTest extends BaseIntegrationTest {
    @Test
    void removeMemberFromProject() throws Exception {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(987);
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

        if (this.projectRepository.findById(project.getId()).isEmpty()) {
            throw new Exception("Project not properly persisted");
        }

        /*
         The following code will solely check for the request status not being any server error status
         since we can't guarantee the availability/integrity of the chosen data in the external Employee API
         nor the availability of the API itself
        */
        this.mockMvc.perform(delete("/project/{id}", project.getId())
                        .header("Authorization", generateJwt()))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        if (this.projectRepository.findById(project.getId()).isPresent()) {
            throw new Exception("Project was not deleted");
        }
    }
}
