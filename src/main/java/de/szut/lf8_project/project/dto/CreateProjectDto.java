package de.szut.lf8_project.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class CreateProjectDto {

    @NotNull(message = "Assignee ID is mandatory")
    private long assigneeId;

    @NotNull(message = "Client ID is mandatory")
    private long clientId;

    @NotNull(message = "Client Assignee ID is mandatory")
    private long clientAssigneeId;

    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @Size(max = 255, message = "Comment must be less than 255 characters")
    private String comment;

    private Date plannedEndDate;
    private Date startDate;
    private Date endDate;

}
