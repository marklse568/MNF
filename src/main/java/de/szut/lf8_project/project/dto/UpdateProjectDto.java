package de.szut.lf8_project.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class UpdateProjectDto {
    @NotNull(message = "Project ID is mandatory")
    private long id;

    private long assigneeId;
    private long clientId;
    private long clientAssigneeId;

    @Size(min = 3, max = 255, message = "Description must be between 3 and 255 characters")
    private String description;

    @Size(max = 255, message = "Comment must be less than 255 characters")
    private String comment;

    private Date plannedEndDate;
    private Date startDate;
    private Date endDate;
}
