package de.szut.lf8_project.project;

import de.szut.lf8_project.exceptionHandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    public ProjectEntity create(ProjectEntity entity) {
        return this.repo.save(entity);
    }

    public ProjectEntity readById(long id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Failed to resolve project with id "+ id));
    }

    public List<ProjectEntity> readAll() {
        return this.repo.findAll();
    }
}

