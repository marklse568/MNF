package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FindAllProjectsTest extends AbstractIntegrationTest {
    @Test
    void findAllProjects() throws Exception {
        final ProjectEntity project = new ProjectEntity();
        project.setAssigneeId(123);
        project.setClientId(456);
        project.setClientAssigneeId(789);
        project.setComment("to the moon");
        this.projectRepository.save(project);

        this.mockMvc.perform(get("/project"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].assigneeId", is(123)))
                .andExpect(jsonPath("$.[0].clientId", is(456)))
                .andExpect(jsonPath("$.[0].clientAssigneeId", is(789)))
                .andExpect(jsonPath("$.[0].comment", is("to the moon")))
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
}
