package de.szut.lf8_project.project;

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

    public ProjectMapper(ProjectService service) {
        this.service = service;
    }

    public GetProjectDto mapEntityToGetDto(ProjectEntity entity) {
        return new GetProjectDto(
                entity.getId(),
                entity.getAssigneeId(),
                entity.getClientId(),
                entity.getClientAssigneeId(),
                entity.getName(),
                entity.getComment(),
                entity.getPlannedEndDate(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }

    public List<GetProjectDto> mapEntityToGetDto(List<ProjectEntity> entities) {
        return entities.stream().map(this::mapEntityToGetDto).collect(Collectors.toList());
    }

    public ProjectEntity mapCreateDtoToEntity(CreateProjectDto dto) {
        return createNewEntity(dto);
    }

    public ProjectEntity mapUpdateDtoToEntity(UpdateProjectDto dto) {
        return createNewEntity(dto);
    }

    public ProjectEntity createNewEntity(CreateProjectDto dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setAssigneeId(dto.getAssigneeId());
        entity.setClientId(dto.getClientId());
        entity.setClientAssigneeId(dto.getClientAssigneeId());
        entity.setName(dto.getName());

        entity.setComment(dto.getComment());
        entity.setStartDate(dto.getStartDate());
        entity.setPlannedEndDate(dto.getPlannedEndDate());
        entity.setEndDate(dto.getEndDate());
        return entity;
    }

    public GetProjectEmployeesDto mapEntityToGetEmployeesDto(ProjectEntity entity) {
        return new GetProjectEmployeesDto(
                entity.getId(),
                entity.getName(),
                entity.getEmployees()
        );
    }
    
    public ProjectEntity createNewEntity(UpdateProjectDto dto) {
        ProjectEntity entity = service.readById(dto.getId());
        if (entity == null) {
            throw new ResourceNotFoundException("Project with id " + dto.getId() + " not found");
        }
        entity.setId(dto.getId());

        if (dto.getAssigneeId() != 0) {
            entity.setAssigneeId(dto.getAssigneeId());
        }
        if (dto.getClientId() != 0) {
            entity.setClientId(dto.getClientId());
        }
        if (dto.getClientAssigneeId() != 0) {
            entity.setClientAssigneeId(dto.getClientAssigneeId());
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
}
