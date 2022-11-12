package de.szut.lf8_project.employee;

import de.szut.lf8_project.employee.employee_project.EmployeeProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employee")
    private Set<EmployeeProjectEntity> joinedProjects = new HashSet<>();

    @Id
    private long id;
}
