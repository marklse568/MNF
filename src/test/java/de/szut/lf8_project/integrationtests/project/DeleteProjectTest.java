package de.szut.lf8_project.integrationtests.project;

import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteProjectTest extends AbstractIntegrationTest {
    
    @Test
    void deleteProject() throws Exception {
        final ProjectEntity deleteEntity = new ProjectEntity();
        deleteEntity.setId(111);
        deleteEntity.setComment("deleted successfully");
        this.projectRepository.delete(deleteEntity);
        this.projectRepository.save(deleteEntity);
        
        String content = "{\n" +
        "\t\"id\": 111,\n" + 
        "\t\"comment\": \"deleted successfully\"\n" + "}";   
        
        final String contentAsString = 
                this.mockMvc.perform(delete("/id").content(content).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id", is(111)))
                        .andExpect(jsonPath("comment", is("deleted successfully")))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
        
        final long id = Long.parseLong(new JSONObject(contentAsString).getString("id").toString());
        
        
    }
    
}
