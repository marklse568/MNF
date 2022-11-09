package de.szut.lf8_project.employee;

import de.szut.lf8_project.employee.dto.EmployeeGetDto;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {
    public EmployeeGetDto mapToGetDto(EmployeeEntity employeeEntity) {
        return new EmployeeGetDto(
            employeeEntity.getQualification(),
            employeeEntity.getProject().getId(),
            employeeEntity.getProject().getName(),
            employeeEntity.getId()
        );
    }
}
