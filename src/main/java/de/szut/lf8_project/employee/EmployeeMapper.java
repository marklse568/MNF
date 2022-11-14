package de.szut.lf8_project.employee;

import de.szut.lf8_project.employee.dto.GetEmployeeProjectsDto;
import de.szut.lf8_project.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.project.dto.GetProjectForEmployeeDto;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EmployeeMapper {
    GetEmployeeProjectsDto mapEntityToGetEmployeeProjectsDto(EmployeeEntity entity) {
        var employeeProjectEntities = entity.getJoinedProjects();
        return new GetEmployeeProjectsDto(
                entity.getId(),
                employeeProjectEntities.stream().map(this::mapGetProjectForEmployeeDto).collect(Collectors.toSet())
        );
    }

    public GetProjectForEmployeeDto mapGetProjectForEmployeeDto(EmployeeProjectEntity entity) {
        return new GetProjectForEmployeeDto(
                entity.getProject().getId(),
                entity.getProject().getName(),
                entity.getProject().getPlannedEndDate(),
                entity.getProject().getStartDate(),
                entity.getProject().getEndDate(),
                entity.getQualification()
        );
    }
}
