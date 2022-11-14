package de.szut.lf8_project.employee_project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.project.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "employee_project")
public class EmployeeProjectEntity {
    @EmbeddedId
    private EmployeeProjectKey id = new EmployeeProjectKey();

    @ManyToOne
    @MapsId("employeeid")
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne
    @MapsId("projectid")
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    private String qualification;
}
