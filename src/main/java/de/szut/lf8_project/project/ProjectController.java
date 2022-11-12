package de.szut.lf8_project.project;

import de.szut.lf8_project.api.EmployeeApiService;
import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.EmployeeService;
import de.szut.lf8_project.employee.dto.GetEmployeeDto;
import de.szut.lf8_project.project.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "project")
public class ProjectController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final ProjectMapper mapper;
    private final EmployeeApiService employeeApiService;

    public ProjectController(ProjectService projectService,EmployeeService employeeService, ProjectMapper mapper,
                             EmployeeApiService employeeApiService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.mapper = mapper;
        this.employeeApiService = employeeApiService;
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
        return mapper.mapEntityToGetDto(projectService.readAll());
    }

    @Operation(summary = "find project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "project with corresponding id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDto.class))}),
            @ApiResponse(responseCode = "404", description = "project with corresponding id not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping("/{id}")
    public GetProjectDto getProjectById(@PathVariable long id) {
        return this.mapper.mapEntityToGetDto(projectService.readById(id));
    }

    @Operation(summary = "creates a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @PostMapping
    public GetProjectDto createProject(@RequestBody @Valid CreateProjectDto dto) {
        ProjectEntity project = this.mapper.mapCreateDtoToEntity(dto);
        ProjectEntity createdProject = this.projectService.create(project);
        return this.mapper.mapEntityToGetDto(createdProject);
    }

    @Operation(summary = "find all employees by project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetEmployeeDto.class))}),
            @ApiResponse(responseCode = "404", description = "project with this id was not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content)})
    @GetMapping("/{id}/employees")
    public GetProjectEmployeesDto getAllEmployeesOfProjectByProjectId(@PathVariable long id) {
        return this.mapper.mapEntityToGetProjectEmployeesDto(projectService.readById(id));
    }

    @Operation(summary = "add employee to project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "employee added to project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDto.class))}),
            @ApiResponse(responseCode = "404", description = "project with this id was not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content)})
    @PutMapping("/{id}/employees/{employeeId}")
    public GetProjectEmployeesDto addEmployeeToProject(@PathVariable long id, @PathVariable long employeeId,
                                                       @RequestBody @Valid AddUserToProjectDto dto,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        this.employeeApiService.validateEmployeeIdAndQualification(employeeId, dto.getQualification(), authorization);
        ProjectEntity project = projectService.readById(id);
        EmployeeEntity employee = employeeService.readById(employeeId);
        var updatedProject = this.projectService.addEmployeeToProject(project, employee, dto.getQualification());
        return this.mapper.mapEntityToGetProjectEmployeesDto(updatedProject);
    }

    @Operation(summary = "updates a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "updated project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @PutMapping
    public GetProjectDto updateProject(@RequestBody @Valid UpdateProjectDto dto) {
        ProjectEntity newProject = this.mapper.mapUpdateDtoToEntity(dto);
        ProjectEntity updatedProject = this.projectService.update(newProject);
        return this.mapper.mapEntityToGetDto(updatedProject);
    }
}
