package de.szut.lf8_project.project.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends  RuntimeException {
 
    public ResourceNotFoundException(String message) {super(message);}
    
}
