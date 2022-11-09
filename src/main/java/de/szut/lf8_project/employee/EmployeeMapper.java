package de.szut.lf8_project.employee;

import de.szut.lf8_project.employee.dto.EmployeeGetDto;

public class EmployeeMapper {
    public EmployeeGetDto mapToGetDto(EmployeeEntity employeeEntity) {

        //TODO: replace null with the actual integration
        return new EmployeeGetDto(
                employeeEntity.getQualification(),
                0,
                null,
                employeeEntity.getId()
        );
    }



}
