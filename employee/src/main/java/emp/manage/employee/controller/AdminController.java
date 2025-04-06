package emp.manage.employee.controller;

import emp.manage.employee.entity.Admin;
import emp.manage.employee.entity.Employee;
import emp.manage.employee.service.AdminService;
import emp.manage.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;
    private final AdminService adminService;

    public AdminController(EmployeeService employeeService, AdminService adminService) {
        this.employeeService = employeeService;
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public Admin register(@RequestBody Admin admin) {
        return adminService.register(admin);
    }

    @PostMapping("/login")
    public Admin login(@RequestBody Admin admin) {
        return adminService.login(admin.getUsername(), admin.getPassword());
    }

    @GetMapping("/employees")
    public List<Employee> allEmployees() {
        return employeeService.getAll();
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee e) {
        return employeeService.save(e);
    }

    @PutMapping("/employees/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee e) {
        e.setId(id);
        return employeeService.save(e);
    }

    @DeleteMapping("/employees/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.softDelete(id);
    }
}
