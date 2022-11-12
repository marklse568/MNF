package de.szut.lf8_project.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class AddUserToProjectDto {
    @Size(min = 3, max = 255, message = "Qualification must be between 3 and 255 characters")
    private String qualification;
}
