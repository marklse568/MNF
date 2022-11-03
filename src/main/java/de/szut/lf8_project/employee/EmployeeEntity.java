package de.szut.lf8_project.employee;

import java.util.UUID;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Employee")
public class EmployeeEntity {

    private String qualification;

    private UUID projectId;

    private String projectName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
