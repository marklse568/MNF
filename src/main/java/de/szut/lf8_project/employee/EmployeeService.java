package de.szut.lf8_project.employee;

import de.szut.lf8_project.hello.HelloEntity;
import de.szut.lf8_project.hello.HelloRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeEntity> readAll() {
        return this.repository.findAll();
    }


}
