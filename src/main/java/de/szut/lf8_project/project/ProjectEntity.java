package de.szut.lf8_project.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee_project.EmployeeProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private long clientId;
    private String clientContactPersonInfo;
    private String name;
    private String comment;
    private LocalDate plannedEndDate;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    private EmployeeEntity responsibleEmployee;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
    private Set<EmployeeProjectEntity> joinedEmployees = new HashSet<>();
}
