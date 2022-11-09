package de.szut.lf8_project.employee;

import de.szut.lf8_project.employee.dto.EmployeeGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;

    public EmployeeController(EmployeeService service, EmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "delivers a list of Employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeGetDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping
    public List<EmployeeGetDto> getAllEmployees() {
        return this.service
                .readAll()
                .stream()
                .map(this.mapper::mapToGetDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "find all employees by project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "project with this id was not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping("/{id}")
    public List<EmployeeGetDto> getAllEmployeesByProjectId(@PathVariable long id) {
        return this.service
                .readAllEmployeesByProjectId(id)
                .stream()
                .map(this.mapper::mapToGetDto)
                .collect(Collectors.toList());
    }
}
