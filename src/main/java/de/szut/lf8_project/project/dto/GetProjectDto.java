package de.szut.lf8_project.project.dto;

import de.szut.lf8_project.employee.dto.GetEmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    private Date plannedEndDate;
    private Date startDate;
    private Date endDate;

    private Set<GetEmployeeDto> employees;
}
