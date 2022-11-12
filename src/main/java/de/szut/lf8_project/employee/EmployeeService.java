package de.szut.lf8_project.employee;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_project.project.ProjectEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeEntity> readAll() {
        return this.repository.findAll();
    }

    public EmployeeEntity readById(long id) {
        var e = this.repository.findById(id);
        if (e.isPresent()) {
            return e.get();
        }

        var newEmployee = new EmployeeEntity();
        newEmployee.setId(id);
        return this.create(newEmployee);
    }

    public EmployeeEntity create(EmployeeEntity entity) {
        return this.repository.saveAndFlush(entity);
    }
}
