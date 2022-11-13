package de.szut.lf8_project.project;

import de.szut.lf8_project.employee.EmployeeEntity;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectEntity;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectKey;
import de.szut.lf8_project.employee.employee_project.EmployeeProjectRepository;
import de.szut.lf8_project.exceptionHandling.ConflictException;
import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository repo;
    private final EmployeeProjectRepository entityLinkRepo;

    public ProjectService(ProjectRepository repo, EmployeeProjectRepository entityLinkRepo) {
        this.repo = repo;
        this.entityLinkRepo = entityLinkRepo;
    }

    public ProjectEntity create(ProjectEntity entity) {
        return this.repo.saveAndFlush(entity);
    }

    public ProjectEntity update(ProjectEntity entity) {
        return this.repo.saveAndFlush(entity);
    }

    public EmployeeProjectEntity addEmployeeToProject(ProjectEntity project, EmployeeEntity employee, String qualification) throws ConflictException {
        var key = new EmployeeProjectKey(employee.getId(), project.getId());

        if (this.entityLinkRepo.findById(key).isPresent()) {
            throw new ConflictException("Employee with id " + employee.getId() +
                    " is already a member of the project with id " + project.getId());
        }

        var employeeProjectEntity = new EmployeeProjectEntity();
        employeeProjectEntity.setEmployee(employee);
        employeeProjectEntity.setProject(project);
        employeeProjectEntity.setQualification(qualification);
        return this.entityLinkRepo.saveAndFlush(employeeProjectEntity);
    }

    public void removeEmployeeFromProject(ProjectEntity project, EmployeeEntity employee) throws ResourceNotFoundException {
        var key = new EmployeeProjectKey(employee.getId(), project.getId());
        var link = this.entityLinkRepo.findById(key);

        if (link.isEmpty()) {
            throw new ResourceNotFoundException("Employee with id " + employee.getId() +
                    " is not a member of the project with id " + project.getId());
        }

        this.entityLinkRepo.delete(link.get());
    }

    public ProjectEntity readById(long id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Failed to resolve project with id " + id));
    }

    public List<ProjectEntity> readAll() {
        return this.repo.findAll();
    }

    public void deleteById(long id) {
        this.repo.deleteById(id);
    }
}

