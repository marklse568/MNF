package de.szut.lf8_project.api;

import de.szut.lf8_project.api.dto.EmployeeDto;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.exceptionHandling.UnauthorizedException;
import de.szut.lf8_project.exceptionHandling.UnknownApiException;
import de.szut.lf8_project.exceptionHandling.UnprocessableEntityException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

        ResponseEntity<EmployeeDto[]> response = null;
        try {
            response = this.restTemplate.exchange(this.url, HttpMethod.GET, request, EmployeeDto[].class);
        } catch (HttpClientErrorException clientException) {
            this.handleClientException(clientException.getStatusCode());
        } catch (Exception genericException) {
            throw new UnknownApiException("Employee API request failed with error " + genericException.getMessage());
        }

        assert response != null;
        return this.handleResponse(response);
    }

    public EmployeeDto getEmployeeById(long id, String authorizationHeader) {
        HttpEntity<Void> entity = new HttpEntity<>(this.createHeaders(authorizationHeader));

        ResponseEntity<EmployeeDto> response = null;
        try {
            response = this.restTemplate.exchange(this.url + "/{id}", HttpMethod.GET, entity, EmployeeDto.class, (int)id);
        } catch (HttpClientErrorException clientException) {
            if (clientException.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException("Failed to resolve employee with id " + id);
            }
            this.handleClientException(clientException.getStatusCode());
        } catch (Exception genericException) {
            throw new UnknownApiException("Employee API request failed with error " + genericException.getMessage());
        }

        assert response != null;
        return this.handleResponse(response);
    }

    public void validateEmployeeIdAndQualification(long id, String qualification, String authorizationHeader) {
        var remoteEmployee = this.getEmployeeById(id, authorizationHeader);

        if (!remoteEmployee.getSkillSet().contains(qualification)) {
            throw new ResourceNotFoundException("Employee with id " + id + " does not contain the qualification " +
                    qualification);
        }
    }

    private HttpHeaders createHeaders(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        return headers;
    }

    private void handleClientException(HttpStatus status) {
        if (status == HttpStatus.UNAUTHORIZED) {
            throw new UnauthorizedException("Failed to authorize to Employee API");
        }
        throw new UnknownApiException("Employee API request failed with status " + status.toString());
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
