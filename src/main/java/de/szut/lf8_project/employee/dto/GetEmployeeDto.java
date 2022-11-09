package de.szut.lf8_project.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetEmployeeDto {

    private String qualification;

    private long id;
}
