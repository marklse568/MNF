package de.szut.lf8_project.project;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "project")
public class projectController {
    private final projectService service;

    public projectController(projectService service) {
        this.service = service;
    }
}
