package de.szut.lf8_project.project;

import de.szut.lf8_project.api.CustomerApiService;
import de.szut.lf8_project.api.EmployeeApiService;
import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.EmployeeService;
import de.szut.lf8_project.employee.dto.GetEmployeeDto;
import de.szut.lf8_project.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.employee_project.EmployeeProjectService;
import de.szut.lf8_project.employee_project.dto.QualificationDto;
import de.szut.lf8_project.project.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "project")
public class ProjectController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final ProjectMapper mapper;
    private final EmployeeApiService employeeApiService;
    private final CustomerApiService customerApiService;
    private final EmployeeProjectService employeeProjectService;

    public ProjectController(ProjectService projectService, EmployeeService employeeService, ProjectMapper mapper,
                             EmployeeApiService employeeApiService, CustomerApiService customerApiService, EmployeeProjectService employeeProjectService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.mapper = mapper;
        this.employeeApiService = employeeApiService;
        this.customerApiService = customerApiService;
        this.employeeProjectService = employeeProjectService;
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
    public ResponseEntity<GetProjectDto> createProject(@RequestBody @Valid CreateProjectDto dto,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        this.employeeApiService.validateEmployeeId(dto.getResponsibleEmployeeId(), authorization);
        ProjectEntity project = this.mapper.mapCreateDtoToEntity(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.mapper.mapEntityToGetDto(this.projectService.create(project)));
    }

    @Operation(summary = "updates a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @PutMapping
    public GetProjectDto updateProject(@RequestBody @Valid UpdateProjectDto dto,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        if (dto.getClientId() != 0) {
            this.customerApiService.validateClientId(dto.getClientId(), authorization);
        }

        ProjectEntity updatedProject = this.mapper.mapUpdateDtoToEntity(dto);
        updatedProject = this.projectService.update(updatedProject);
        return this.mapper.mapEntityToGetDto(updatedProject);
    }

    @Operation(summary = "delivers a list of projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of projects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectDto[].class))}),
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

    @Operation(summary = "deletes a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "project deleted"),
            @ApiResponse(responseCode = "404", description = "project with this id was not found"),
            @ApiResponse(responseCode = "401", description = "not authorized")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable long id) {
        ProjectEntity project = projectService.readById(id);
        this.projectService.deleteById(project.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "find a list of employees by project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectEmployeesDto.class))}),
            @ApiResponse(responseCode = "404", description = "project with this id was not found"),
            @ApiResponse(responseCode = "401", description = "not authorized")})
    @GetMapping("/{id}/employees")
    public GetProjectEmployeesDto getAllEmployeesOfProjectByProjectId(@PathVariable long id) {
        return this.mapper.mapEntityToGetProjectEmployeesDto(projectService.readById(id));
    }

    @Operation(summary = "find a suitable employee for a project based on qualification and time slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of suitable employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectEmployeesDto.class))}),
            @ApiResponse(responseCode = "404", description = "project with this id was not found"),
            @ApiResponse(responseCode = "401", description = "not authorized")})
    @GetMapping("/{id}/employees/suitable")
    public List<GetEmployeeDto> getSuitableEmployeesForProjectByProjectIdAndQualification(@PathVariable long id,
                                                                                          @RequestBody @Valid QualificationDto dto) {
        var project = projectService.readById(id);
        var startDate = project.getStartDate();
        var endDateOrPlannedEndDate = project.getEndDate() != null ? project.getEndDate() : project.getPlannedEndDate();

        var suitableEmployees = this.employeeProjectService.findAllByQualificationAndTimespan(dto.getQualification(),
                startDate, endDateOrPlannedEndDate);
        
        return suitableEmployees.stream().map(this.mapper::mapEmployeeProjectEntityToGetEmployeeDto).collect(Collectors.toList());
    }

    @Operation(summary = "add employee to project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "employee added to project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetProjectEmployeesDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted"),
            @ApiResponse(responseCode = "404", description = "project with this id was not found"),
            @ApiResponse(responseCode = "401", description = "not authorized")})
    @PutMapping("/{id}/employees/{employeeId}")
    public GetProjectEmployeesDto addEmployeeToProject(@PathVariable long id, @PathVariable long employeeId,
                                                       @RequestBody @Valid AddUserToProjectDto dto,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        this.employeeApiService.validateEmployeeIdAndQualification(employeeId, dto.getQualification(), authorization);
        ProjectEntity project = projectService.readById(id);
        EmployeeEntity employee = employeeService.readOrCreateById(employeeId);
        EmployeeProjectEntity employeeProjectEntity = this.projectService.addEmployeeToProject(project, employee, dto.getQualification());
        return this.mapper.mapEntityToGetProjectEmployeesDto(employeeProjectEntity.getProject());
    }

    @Operation(summary = "remove employee from project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "removed employee from project"),
            @ApiResponse(responseCode = "404", description = "project with this id was not found"),
            @ApiResponse(responseCode = "401", description = "not authorized")})
    @DeleteMapping("/{id}/employees/{employeeId}")
    public ResponseEntity<Void> removeEmployeeFromProject(@PathVariable long id, @PathVariable long employeeId,
                                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        this.employeeApiService.validateEmployeeId(employeeId, authorization);
        ProjectEntity project = projectService.readById(id);
        EmployeeEntity employee = employeeService.readById(employeeId);
        this.projectService.removeEmployeeFromProject(project, employee);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
