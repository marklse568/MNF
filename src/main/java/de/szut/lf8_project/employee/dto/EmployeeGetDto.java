package de.szut.lf8_project.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeGetDto {

    private String qualification;

    private long projectId;

    private String projectName;

    private long id;
}
