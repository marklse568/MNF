package de.szut.lf8_project.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.szut.lf8_project.testcontainers.AbstractIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class BaseIntegrationTest extends AbstractIntegrationTest {

    protected String generateJwt() throws JsonProcessingException {
        String jsonResponse = new RestTemplate()
                .postForEntity("https://keycloak.szut.dev/auth/realms/szut/protocol/openid-connect/token",
                        computeRequest(), String.class).getBody();

        var map = new ObjectMapper().readValue(jsonResponse, Map.class);

        return "Bearer " + map.get("access_token");
    }

    private HttpEntity<MultiValueMap<String, String>> computeRequest() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("username", "user");
        body.add("password", "test");
        body.add("client_id", "employee-management-service");
        body.add("grant_type", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return new HttpEntity<MultiValueMap<String, String>>(body, headers);
    }
}
