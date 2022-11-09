package de.szut.lf8_project.api;

import de.szut.lf8_project.api.dto.EmployeeDto;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.exceptionHandling.UnauthorizedException;
import de.szut.lf8_project.exceptionHandling.UnknownApiException;
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
        return this.handleResponse(response);
    }

    public EmployeeDto getEmployeeById(int id, String authorizationHeader) {
        HttpEntity<Void> entity = new HttpEntity<>(this.createHeaders(authorizationHeader));
        ResponseEntity<EmployeeDto> response = this.restTemplate.exchange(this.url + "/%d", HttpMethod.GET, entity,
                EmployeeDto.class, id);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ResourceNotFoundException("Failed to resolve employee with id " + id);
        }

        return this.handleResponse(response);
    }

    private HttpHeaders createHeaders(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        return headers;
    }

    private <T> T handleResponse(ResponseEntity<T> response) {
        switch (response.getStatusCode()) {
            case UNAUTHORIZED: {
                throw new UnauthorizedException("Failed to authorize to Employee API");
            }
            case OK:
            case CREATED:
            case NO_CONTENT: {
                return response.getBody();
            }
            default: {
                throw new UnknownApiException("Employee API request failed with status " + response.getStatusCodeValue());
            }
        }
    }
}
