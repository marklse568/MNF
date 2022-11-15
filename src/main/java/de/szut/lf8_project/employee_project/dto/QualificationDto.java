package de.szut.lf8_project.employee_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QualificationDto {
    @NotBlank(message = "Qualification is mandatory")
    private String qualification;
}
