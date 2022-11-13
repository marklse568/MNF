package de.szut.lf8_project.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateProjectDto {
    @NotNull(message = "Project ID is mandatory")
    private long id;

    private long clientId;
    private String clientContactPersonInfo;

    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @Size(max = 255, message = "Comment must be less than 255 characters")
    private String comment;

    private LocalDate plannedEndDate;
    private LocalDate startDate;
    private LocalDate endDate;
}
