package de.szut.lf8_project.project;

import de.szut.lf8_project.hello.HelloEntity;
import de.szut.lf8_project.hello.dto.HelloCreateDto;
import de.szut.lf8_project.project.dto.ProjectCreateDto;
import de.szut.lf8_project.project.dto.ProjectGetDto;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectMapper {
    public ProjectGetDto projectGetDtoMap(ProjectEntity entity) {return new ProjectGetDto(entity.getId(), entity.getCustomerId(), entity.getChargePersonId(), entity.getProjectDescription(), entity.getComment(), entity.getPersonInCharge(), entity.getStartDate(), 
            entity.getPlannedEndDate(), entity.getEndDate());}
    
    public ProjectEntity CreateDtoToEntity(ProjectCreateDto dto) {
        var entity = new ProjectEntity();
        entity.setProjectDescription(entity.getProjectDescription());
        entity.setComment(entity.getComment());
        entity.setStartDate(entity.getStartDate());
        entity.setPlannedEndDate(entity.getPlannedEndDate());
        entity.setEndDate(entity.getEndDate());
        return entity;
    }
}
