package de.szut.lf8_project.employee_project;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class EmployeeProjectKey implements Serializable {
    @Column(name = "employee_id")
    Long employeeid;

    @Column(name = "project_id")
    Long projectid;
}
