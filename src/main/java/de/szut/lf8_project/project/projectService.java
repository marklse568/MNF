package de.szut.lf8_project.project;

import de.szut.lf8_project.hello.HelloEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class projectService {

    private final projectRepository repo;

    public projectService(projectRepository repo) {
        this.repo = repo;
    }

    public projectEntity create(projectEntity entity) {
        return this.repo.save(entity);
    }

    public List<projectEntity> readAll() {
        return this.repo.findAll();
    }
}

