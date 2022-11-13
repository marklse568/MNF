package de.szut.lf8_project.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class GetProjectForEmployeeDto {
    private long id;
    private String name;
    private LocalDate plannedEndDate;
    private LocalDate startDate;
    private LocalDate endDate;
}
