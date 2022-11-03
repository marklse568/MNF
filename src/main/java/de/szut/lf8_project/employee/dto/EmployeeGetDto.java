package de.szut.lf8_project.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeGetDto {

    private String qualification;

    private UUID projectId;

    private String projectName;

    private long id;
}
