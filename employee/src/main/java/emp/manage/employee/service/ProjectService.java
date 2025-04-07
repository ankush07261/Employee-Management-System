package emp.manage.employee.service;

import emp.manage.employee.entity.Project;
import emp.manage.employee.repository.ProjectRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    @Cacheable("projects")
    public List<Project> getAll() {
        return repo.findAll();
    }

    public Project save(Project p) {
        return repo.save(p);
    }

    @CacheEvict(value = "projects", allEntries = true)
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
