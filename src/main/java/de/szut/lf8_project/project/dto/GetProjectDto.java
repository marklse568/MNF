package de.szut.lf8_project.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.szut.lf8_project.employee.dto.GetEmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class GetProjectDto {
    private long id;
    private long responsibleEmployeeId;
    private long clientId;
    private String clientContactPersonInfo;
    private String name;
    private String comment;

    private LocalDate plannedEndDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private Set<GetEmployeeDto> employees;
}
