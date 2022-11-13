package de.szut.lf8_project.employee;

import de.szut.lf8_project.employee.dto.GetEmployeeDto;
import de.szut.lf8_project.employee.dto.GetEmployeeProjectsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper mapper) {
        this.employeeService = employeeService;
        this.mapper = mapper;
    }

    @Operation(summary = "find all projects of employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "employee id and project list",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetEmployeeDto.class))}),
            @ApiResponse(responseCode = "404", description = "no data found for employee", content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized", content = @Content)})
    @GetMapping("/{id}/projects")
    public GetEmployeeProjectsDto getAllProjectsOfEmployeeById(@PathVariable long id) {
        var employee = this.employeeService.readById(id);
        return this.mapper.mapEntityToGetEmployeeProjectsDto(employee);
    }
}
