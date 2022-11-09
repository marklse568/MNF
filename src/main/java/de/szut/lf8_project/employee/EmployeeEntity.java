package de.szut.lf8_project.employee;

import de.szut.lf8_project.project.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    private String qualification;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employees")
    private Set<ProjectEntity> projects;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    public EmployeeEntity() {
        this.projects = new HashSet<>();
    }

    public void addProject(ProjectEntity projectEntity) {
        projects.add(projectEntity);
        projectEntity.getEmployees().add(this);
    }
}
