package de.szut.lf8_project.employee;

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

    public List<EmployeeEntity> readAllEmployeesByProjectId(long id) {
        return this.repository.findAll(id);
    }
}
