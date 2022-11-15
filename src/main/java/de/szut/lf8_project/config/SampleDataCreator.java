package de.szut.lf8_project.config;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.EmployeeRepository;
import de.szut.lf8_project.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.employee_project.EmployeeProjectRepository;
import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.project.ProjectRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SampleDataCreator implements ApplicationRunner {

    private final ProjectRepository projectRepo;
    private final EmployeeRepository employeeRepo;
    private final EmployeeProjectRepository employeeProjectRepo;

    public SampleDataCreator(ProjectRepository projectRepo, EmployeeRepository employeeRepo, EmployeeProjectRepository employeeProjectRepo) {
        this.projectRepo = projectRepo;
        this.employeeRepo = employeeRepo;
        this.employeeProjectRepo = employeeProjectRepo;
    }

    public void run(ApplicationArguments args) {
        var employeeOne = new EmployeeEntity();
        employeeOne.setId(987);
        employeeOne = this.employeeRepo.saveAndFlush(employeeOne);

        var employeeTwo = new EmployeeEntity();
        employeeTwo.setId(654);
        employeeTwo = this.employeeRepo.saveAndFlush(employeeTwo);

        var employeeThree = new EmployeeEntity();
        employeeThree.setId(312);
        employeeThree = this.employeeRepo.saveAndFlush(employeeThree);

        var projectOne = new ProjectEntity();
        projectOne.setName("Project Beluga");
        projectOne.setResponsibleEmployee(employeeOne);
        projectOne.setClientId(456);
        projectOne.setClientContactPersonInfo("jane@doe.fishing");
        projectOne.setComment("to the moon");
        projectOne.setPlannedEndDate(LocalDate.now().plusYears(3));
        projectOne.setStartDate(LocalDate.now().minusMonths(11));
        projectOne.setEndDate(LocalDate.now().plusYears(4));


        var projectTwo = new ProjectEntity();
        projectTwo.setName("Project Whale");
        projectTwo.setResponsibleEmployee(employeeOne);
        projectTwo.setClientId(222);
        projectTwo.setClientContactPersonInfo("john@doe.fishing");
        projectTwo.setComment("to the stars");
        projectTwo.setPlannedEndDate(LocalDate.now().plusYears(2));
        projectTwo.setStartDate(LocalDate.now().minusMonths(5));
        projectTwo.setEndDate(LocalDate.now().plusYears(3));

        var projectThree = new ProjectEntity();
        projectThree.setName("Project Dolphin");
        projectThree.setResponsibleEmployee(employeeOne);
        projectThree.setClientId(555);
        projectThree.setClientContactPersonInfo("juergen_der_zerstoerer@web.de");
        projectThree.setComment("to the sun");
        projectThree.setPlannedEndDate(LocalDate.now().plusYears(1));
        projectThree.setStartDate(LocalDate.now().minusMonths(2));
        projectThree.setEndDate(LocalDate.now().plusYears(2));

        var projectFour = new ProjectEntity();
        projectFour.setName("Project Shark");
        projectFour.setResponsibleEmployee(employeeOne);
        projectFour.setClientId(111);
        projectFour.setClientContactPersonInfo("ben_dover@mike_litoris.com");
        projectFour.setComment("to the galaxy");
        projectFour.setPlannedEndDate(LocalDate.now().plusYears(14));
        projectFour.setStartDate(LocalDate.now().plusYears(12));

        var qualificationsOne = new EmployeeProjectEntity();
        qualificationsOne.setEmployee(employeeOne);
        qualificationsOne.setProject(projectOne);
        qualificationsOne.setQualification("Javascript");

        var qualificationsTwo = new EmployeeProjectEntity();
        qualificationsTwo.setEmployee(employeeTwo);
        qualificationsTwo.setProject(projectTwo);
        qualificationsTwo.setQualification("Java");

        var qualificationsThree = new EmployeeProjectEntity();
        qualificationsThree.setEmployee(employeeThree);
        qualificationsThree.setProject(projectThree);
        qualificationsThree.setQualification("Python");

        var qualificationsFour = new EmployeeProjectEntity();
        qualificationsFour.setEmployee(employeeOne);
        qualificationsFour.setProject(projectThree);
        qualificationsFour.setQualification("CI/CD");


        this.projectRepo.save(projectOne);
        this.projectRepo.save(projectTwo);
        this.projectRepo.save(projectThree);
        this.projectRepo.save(projectFour);

        this.employeeProjectRepo.save(qualificationsOne);
        this.employeeProjectRepo.save(qualificationsTwo);
        this.employeeProjectRepo.save(qualificationsThree);
        this.employeeProjectRepo.save(qualificationsFour);
    }
}
