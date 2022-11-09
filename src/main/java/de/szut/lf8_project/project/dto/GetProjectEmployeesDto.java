package de.szut.lf8_project.project.dto;

import de.szut.lf8_project.employee.dto.GetEmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class GetProjectEmployeesDto {
    private long id;
    private String name;
    private Set<GetEmployeeDto> employees;
}
