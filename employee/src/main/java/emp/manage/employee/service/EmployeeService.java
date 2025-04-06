package emp.manage.employee.service;

import emp.manage.employee.entity.Employee;
import emp.manage.employee.repository.EmployeeRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public EmployeeService(EmployeeRepository employeeRepository, RedisTemplate<String, Object> redisTemplate) {
        this.employeeRepository = employeeRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Employee> getAll() {
        String cacheKey = "employee::all";
        List<Employee> employees = null;
        Object cachedData = redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            employees = new ObjectMapper().convertValue(cachedData, new TypeReference<List<Employee>>() {});
        }

        if (employees != null) {
            System.out.println("🔥 Loaded from Redis cache");
            return employees;
        }

        employees = employeeRepository.findAll();
        redisTemplate.opsForValue().set(cacheKey, employees, 10, TimeUnit.MINUTES); // optional TTL
        return employees;
    }

    public Employee save(Employee e) {
        Employee saved = employeeRepository.save(e);
        redisTemplate.delete("employee::all"); // bust cache
        return saved;
    }

    public void softDelete(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setLeaveDate(LocalDate.now());
        employeeRepository.save(emp);
        redisTemplate.delete("employee::all"); // bust cache
    }
}
