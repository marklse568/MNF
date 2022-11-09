package de.szut.lf8_project.project;

import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.GetProjectDto;
import de.szut.lf8_project.project.dto.GetProjectEmployeesDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectMapper {

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
        ProjectEntity entity = new ProjectEntity();
        entity.setAssigneeId(dto.getAssigneeId());
        entity.setClientId(dto.getClientId());
        entity.setClientAssigneeId(dto.getClientAssigneeId());
        entity.setName(dto.getDescription());

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
}
