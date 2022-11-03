package de.szut.lf8_project.project;

import de.szut.lf8_project.hello.HelloEntity;
import de.szut.lf8_project.project.dto.ProjectCreateDto;
import de.szut.lf8_project.project.dto.ProjectGetDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "project")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }
    
    @PostMapping 
    public ProjectGetDto create(@RequestBody @Valid ProjectCreateDto projectCreateDto) {
        ProjectEntity entity = this.service.create(new ProjectEntity());
        return null;
    }
}
