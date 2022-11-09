package de.szut.lf8_project.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Employees")
public class EmployeeEntity {

    private String qualification;

    //TODO: relation to Projects

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
