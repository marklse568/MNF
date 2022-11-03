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
public class projectEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private long chargePersonId;
    private long customerId;
    private String PersonInCharge;
    private String ProjectDescription;
    private String comment;
    private Date startDate;
    private Date plannedEndDate;
    private Date endDate;
}
