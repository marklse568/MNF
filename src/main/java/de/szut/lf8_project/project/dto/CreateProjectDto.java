package de.szut.lf8_project.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CreateProjectDto {
    @NotNull(message = "Responsible Employee ID is mandatory")
    private long responsibleEmployeeId;

    @NotNull(message = "Client ID is mandatory")
    private long clientId;

    @Size(min = 3, max = 255, message = "Client contact person info must be between 3 and 255 characters")
    private String clientContactPersonInfo;

    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @Size(max = 255, message = "Comment must be less than 255 characters")
    private String comment;

    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;

    @NotNull(message = "Planned end date is mandatory")
    private LocalDate plannedEndDate;
}
