package emp.manage.employee.service;

import emp.manage.employee.entity.Employee;
import emp.manage.employee.repository.EmployeeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor manually defined
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "employees")
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @CachePut(value = "employees", key = "#e.id")
    public Employee save(Employee e) {
        return employeeRepository.save(e);
    }

    @CacheEvict(value = "employees", key = "#id")
    public void softDelete(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setLeaveDate(LocalDate.now());
        employeeRepository.save(emp);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public Employee register(Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }
}
