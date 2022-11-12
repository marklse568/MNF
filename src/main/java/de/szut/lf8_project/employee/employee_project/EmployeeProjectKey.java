package de.szut.lf8_project.employee.employee_project;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EmployeeProjectKey implements Serializable {
    @Column(name = "employee_id")
    Long employeeid;

    @Column(name = "project_id")
    Long projectid;
}
