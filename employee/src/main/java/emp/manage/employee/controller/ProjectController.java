package emp.manage.employee.controller;

import emp.manage.employee.entity.Project;
import emp.manage.employee.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<Project> all() {
        return service.getAll();
    }

    @PostMapping
    public Project add(@RequestBody Project p) {
        return service.save(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
