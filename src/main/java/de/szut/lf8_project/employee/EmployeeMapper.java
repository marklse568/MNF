package de.szut.lf8_project.employee;

import de.szut.lf8_project.employee.dto.EmployeeGetDto;

public class EmployeeMapper {
    public EmployeeGetDto mapToGetDto(EmployeeEntity e) {
        return new EmployeeGetDto(e.getQualification(), e.getProjectId(), e.getProjectName(), e.getId());
    }

}
