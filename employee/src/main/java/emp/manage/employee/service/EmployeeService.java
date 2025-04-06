package emp.manage.employee.service;

import emp.manage.employee.entity.Employee;
import emp.manage.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee e) {
        return employeeRepository.save(e);
    }

    public void softDelete(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setLeaveDate(LocalDate.now());
        employeeRepository.save(emp);
    }
}
