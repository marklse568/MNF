package de.szut.lf8_project.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private long assigneeId;
    private long clientId;
    private long clientAssigneeId;
    private String name;
    private String comment;
    private Date plannedEndDate;
    private Date startDate;
    private Date endDate;
}
