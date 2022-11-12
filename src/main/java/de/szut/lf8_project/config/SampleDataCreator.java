package de.szut.lf8_project.config;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.EmployeeRepository;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectRepository;
import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.project.ProjectRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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
        var projectOne = new ProjectEntity();
        projectOne.setName("Project Beluga");
        projectOne.setAssigneeId(123);
        projectOne.setClientId(456);
        projectOne.setClientAssigneeId(789);
        projectOne.setComment("to the moon");

        var projectTwo = new ProjectEntity();
        projectTwo.setName("Project Whale");
        projectTwo.setAssigneeId(111);
        projectTwo.setClientId(222);
        projectTwo.setClientAssigneeId(333);
        projectTwo.setComment("to the stars");

        var projectThree = new ProjectEntity();
        projectThree.setName("Project Dolphin");
        projectThree.setAssigneeId(444);
        projectThree.setClientId(555);
        projectThree.setClientAssigneeId(666);
        projectThree.setComment("to the sun");


        var employeeOne = new EmployeeEntity();
        var employeeTwo = new EmployeeEntity();
        var employeeThree = new EmployeeEntity();

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

        this.employeeRepo.save(employeeOne);
        this.employeeRepo.save(employeeTwo);
        this.employeeRepo.save(employeeThree);

        this.employeeProjectRepo.save(qualificationsOne);
        this.employeeProjectRepo.save(qualificationsTwo);
        this.employeeProjectRepo.save(qualificationsThree);
        this.employeeProjectRepo.save(qualificationsFour);
    }
}
