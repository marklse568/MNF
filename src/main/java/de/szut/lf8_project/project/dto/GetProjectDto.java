package de.szut.lf8_project.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class GetProjectDto {
    private long id;
    private long assigneeId;
    private long clientId;
    private long clientAssigneeId;
    private String description;
    private String comment;
    private Date plannedEndDate;
    private Date startDate;
    private Date endDate;
}
