package de.szut.lf8_project.api;

import de.szut.lf8_project.api.dto.EmployeeDto;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.exceptionHandling.UnauthorizedException;
import de.szut.lf8_project.exceptionHandling.UnknownApiException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerApiService {
    public void validateClientId(long id, String authorizationHeader) {
        // this function should throw appropriate exceptions as soon as the Customer API is ready to be implemented
        return;
    }
}
