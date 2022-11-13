package de.szut.lf8_project.project;

import de.szut.lf8_project.employee.EmployeeService;
import de.szut.lf8_project.employee.dto.GetEmployeeDto;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.GetProjectDto;
import de.szut.lf8_project.project.dto.GetProjectEmployeesDto;
import de.szut.lf8_project.project.dto.UpdateProjectDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectMapper {
    private final ProjectService service;
    private final EmployeeService employeeService;

    public ProjectMapper(ProjectService service, EmployeeService employeeService) {
        this.service = service;
        this.employeeService = employeeService;
    }

    public GetProjectDto mapEntityToGetDto(ProjectEntity entity) {
        var employeeProjectEntities = entity.getJoinedEmployees();

        return new GetProjectDto(
                entity.getId(),
                entity.getResponsibleEmployee().getId(),
                entity.getClientId(),
                entity.getClientContactPersonInfo(),
                entity.getName(),
                entity.getComment(),
                entity.getPlannedEndDate(),
                entity.getStartDate(),
                entity.getEndDate(),
                employeeProjectEntities.stream().map(this::mapQualificationEntityToGetDto).collect(Collectors.toSet())
        );
    }

    public GetEmployeeDto mapQualificationEntityToGetDto(EmployeeProjectEntity entity) {
        return new GetEmployeeDto(
                entity.getQualification(),
                entity.getEmployee().getId()
        );
    }

    public List<GetProjectDto> mapEntityToGetDto(List<ProjectEntity> entities) {
        return entities.stream().map(this::mapEntityToGetDto).collect(Collectors.toList());
    }

    public ProjectEntity mapCreateDtoToEntity(CreateProjectDto dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setResponsibleEmployee(this.employeeService.readOrCreateById(dto.getResponsibleEmployeeId()));
        entity.setClientId(dto.getClientId());
        entity.setClientContactPersonInfo(dto.getClientContactPersonInfo());
        entity.setName(dto.getName());

        entity.setComment(dto.getComment());
        entity.setStartDate(dto.getStartDate());
        entity.setPlannedEndDate(dto.getPlannedEndDate());
        return entity;
    }

    public ProjectEntity mapUpdateDtoToEntity(UpdateProjectDto dto) {
        ProjectEntity entity = service.readById(dto.getId());

        if (dto.getClientId() != 0) {
            entity.setClientId(dto.getClientId());
        }
        if (!dto.getClientContactPersonInfo().isEmpty()) {
            entity.setClientContactPersonInfo(dto.getClientContactPersonInfo());
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getComment() != null) {
            entity.setComment(dto.getComment());
        }
        if (dto.getStartDate() != null) {
            entity.setStartDate(dto.getStartDate());
        }
        if (dto.getPlannedEndDate() != null) {
            entity.setPlannedEndDate(dto.getPlannedEndDate());
        }
        if (dto.getEndDate() != null) {
            entity.setEndDate(dto.getEndDate());
        }
        return entity;
    }

    public GetProjectEmployeesDto mapEntityToGetProjectEmployeesDto(ProjectEntity entity) {
        return new GetProjectEmployeesDto(
                entity.getId(),
                entity.getName(),
                entity.getJoinedEmployees().stream().map(this::mapEntityToGetEmployeesDto).collect(Collectors.toSet())
        );
    }

    public GetEmployeeDto mapEntityToGetEmployeesDto(EmployeeProjectEntity entity) {
        return new GetEmployeeDto(
                entity.getQualification(),
                entity.getEmployee().getId()
        );
    }
}
