package de.szut.lf8_project.config;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.EmployeeRepository;
import de.szut.lf8_project.project.ProjectEntity;
import de.szut.lf8_project.project.ProjectRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataCreator implements ApplicationRunner {

    private final ProjectRepository projectRepo;

    public SampleDataCreator(ProjectRepository projectRepo) {
        this.projectRepo = projectRepo;
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
        employeeOne.setQualification("Java");

        var employeeTwo = new EmployeeEntity();
        employeeTwo.setQualification("Python");

        var employeeThree = new EmployeeEntity();
        employeeThree.setQualification("Rust");

        projectOne.addEmployee(employeeOne);

        projectTwo.addEmployee(employeeTwo);
        projectTwo.addEmployee(employeeThree);

        projectThree.addEmployee(employeeOne);
        projectThree.addEmployee(employeeTwo);
        projectThree.addEmployee(employeeThree);

        this.projectRepo.saveAndFlush(projectOne);
        this.projectRepo.saveAndFlush(projectTwo);
        this.projectRepo.saveAndFlush(projectThree);
    }
}
