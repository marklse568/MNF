package de.szut.lf8_project.api;

import de.szut.lf8_project.api.dto.EmployeeDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeApiService {
    private final RestTemplate restTemplate;
    private final String url = "https://employee.szut.dev/employees";

    public EmployeeApiService() {
        this.restTemplate = new RestTemplate();
    }

    public EmployeeDto[] getEmployees(String authorizationHeader) {
        HttpEntity<Void> request = new HttpEntity<>(this.createHeaders(authorizationHeader));
        ResponseEntity<EmployeeDto[]> response = this.restTemplate.exchange(this.url, HttpMethod.GET, request,
                EmployeeDto[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public EmployeeDto getEmployeeById(int id, String authorizationHeader) {
        HttpEntity<Void> entity = new HttpEntity<>(this.createHeaders(authorizationHeader));
        ResponseEntity<EmployeeDto> response = this.restTemplate.exchange(this.url + "/%d", HttpMethod.GET, entity,
                EmployeeDto.class, id);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    private HttpHeaders createHeaders(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authorizationHeader);
        return headers;
    }
}
