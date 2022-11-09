package de.szut.lf8_project.project;

import de.szut.lf8_project.employee.dto.EmployeeGetDto;
import de.szut.lf8_project.hello.dto.HelloGetDto;
import de.szut.lf8_project.project.dto.CreateProjectDto;
import de.szut.lf8_project.project.dto.GetProjectDto;
import de.szut.lf8_project.project.dto.GetProjectEmployeesDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "project")
public class ProjectController {
    private final ProjectService service;
    private final ProjectMapper mapper;

    public ProjectController(ProjectService service, ProjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "delivers a list of projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of projects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping
    public List<GetProjectDto> getAllProjects() {
        return mapper.mapEntityToGetDto(service.readAll());
    }

    @Operation(summary = "find project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "project with corresponding id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HelloGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "project with corresponding id not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping("/{id}")
    public GetProjectDto getProjectById(@PathVariable long id) {
        return this.mapper.mapEntityToGetDto(service.readById(id));
    }

    @Operation(summary = "creates a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HelloGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @PostMapping
    public GetProjectDto createProject(@RequestBody @Valid CreateProjectDto dto) {
        ProjectEntity project = this.mapper.mapCreateDtoToEntity(dto);
        ProjectEntity createdProject = this.service.create(project);
        return this.mapper.mapEntityToGetDto(createdProject);
    }

    @Operation(summary = "find all employees by project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "project with this id was not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content)})
    @GetMapping("/{id}/employees")
    public GetProjectEmployeesDto getAllEmployeesOfProjectByProjectId(@PathVariable long id) {
        return this.mapper.mapEntityToGetEmployeesDto(service.readById(id));
    }
}
