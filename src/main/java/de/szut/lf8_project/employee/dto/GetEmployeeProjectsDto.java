package de.szut.lf8_project.employee.dto;

import de.szut.lf8_project.project.dto.GetProjectForEmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class GetEmployeeProjectsDto {
    private long id;
    private Set<GetProjectForEmployeeDto> projects;
}
