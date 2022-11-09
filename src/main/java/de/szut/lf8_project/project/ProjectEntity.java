package de.szut.lf8_project.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private long assigneeId;
    private long clientId;
    private long clientAssigneeId;
    private String name;
    private String comment;
    private Date plannedEndDate;
    private Date startDate;
    private Date endDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "project_employee", joinColumns = @JoinColumn(name = "projects"), inverseJoinColumns =
    @JoinColumn(name = "employees"))
    private Set<EmployeeEntity> employees;

    public ProjectEntity() {
        this.employees = new HashSet<>();
    }

    public void addEmployee(EmployeeEntity employeeEntity) {
        employees.add(employeeEntity);
        employeeEntity.addProject(this);
    }
}
