package de.szut.lf8_project.employee;

import de.szut.lf8_project.project.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    private String qualification;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProjectEntity project;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
