package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.integrationtests.BaseIntegrationTest;
import de.szut.lf8_project.project.ProjectEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class AddMemberToProjectTest extends BaseIntegrationTest {
    @Test
    void addMemberToProject() throws Exception {
        final ProjectEntity project = new ProjectEntity();
        project.setAssigneeId(123);
        project.setClientId(456);
        project.setClientAssigneeId(789);
        project.setComment("to the moon");
        this.projectRepository.save(project);

        long projectId = project.getId();

        String content = "{\"qualification\": \"Modellbahn-Purist\"}";

        /*
         The following code will solely check for the request status not being any server error status
         since we can't guarantee the availability/integrity of the chosen data in the external Employee API
        */
        this.mockMvc.perform(put(String.format("/project/%d/employees/9", projectId))
                        .content(content).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", generateJwt()))
                .andExpect(mvcResult -> assertThat(mvcResult.getResponse().getStatus()).isLessThan(500))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
