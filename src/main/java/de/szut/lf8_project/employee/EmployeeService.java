package de.szut.lf8_project.employee;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
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
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Failed to resolve data for employee with id " + id));
    }

    public EmployeeEntity readOrCreateById(long id) {
        EmployeeEntity employee;
        try {
            employee = this.readById(id);
        } catch (ResourceNotFoundException e) {
            var newEmployee = new EmployeeEntity();
            newEmployee.setId(id);
            return this.create(newEmployee);
        }
        return employee;
    }

    public EmployeeEntity create(EmployeeEntity entity) {
        return this.repository.saveAndFlush(entity);
    }
}
