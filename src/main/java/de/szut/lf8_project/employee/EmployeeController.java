package de.szut.lf8_project.employee;

import de.szut.lf8_project.employee.dto.EmployeeGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeController {
    private final EmployeeService service;
    private final EmployeeMapper EmployeeMapper;

    public EmployeeController(EmployeeService service, EmployeeMapper mappingService) {
        this.service = service;
        this.EmployeeMapper = mappingService;
    }

    @Operation(summary = "delivers a list of Employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of Employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeGetDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping
    public List<EmployeeGetDto> findAll() {
        return this.service
                .readAll()
                .stream()
                .map(e -> this.EmployeeMapper.mapToGetDto(e))
                .collect(Collectors.toList());
    }

}
