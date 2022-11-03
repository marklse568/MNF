package de.szut.lf8_project.project;

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

    public List<ProjectEntity> readAll() {
        return this.repo.findAll();
    }
}

