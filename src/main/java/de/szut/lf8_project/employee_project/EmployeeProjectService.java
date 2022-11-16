package de.szut.lf8_project.employee_project;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeProjectService {

    private final EmployeeProjectRepository repository;

    public EmployeeProjectService(EmployeeProjectRepository repository) {
        this.repository = repository;
    }

    public List<EmployeeProjectEntity> readAll() {
        return this.repository.findAll();
    }

    private List<EmployeeProjectEntity> findAllByQualification(String qualification) {
        var employees = this.readAll();
        employees.removeIf(e -> !e.getQualification().equals(qualification));
        return employees;
    }

    private boolean isNotAvailable(EmployeeProjectEntity entity, LocalDate startDate, LocalDate endDate) {
        LocalDate endOrPlannedEndDate = entity.getProject().getEndDate() == null ?
                entity.getProject().getPlannedEndDate() :
                entity.getProject().getEndDate();

        return entity.getProject().getStartDate().isBefore(endDate) && startDate.isBefore(endOrPlannedEndDate);
    }

    public List<EmployeeProjectEntity> findAllByQualificationAndTimespan(String qualification,
                                                                         LocalDate startDate, LocalDate endDate) {
        var employees = this.findAllByQualification(qualification);
        employees.removeIf(e -> !e.getQualification().equals(qualification) || this.isNotAvailable(e, startDate, endDate));
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employee with the qualification " + qualification + " is available in " +
                    "the given timespan");
        }
        return employees;
    }
}
