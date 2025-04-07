package emp.manage.employee.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class Employee implements UserDetails {
    private Long id;
    private String email;
    private String department;
    private double salary;
    private String password;
    private String role; // Example: "ROLE_USER" or "ROLE_ADMIN"

    // Getters and setters for id, email, password, and role

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the role as a GrantedAuthority
        return Collections.singleton(() -> role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getRole() { // Added getter for role
        return role;
    }

    public void setRole(String role) { // Added setter for role
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private LocalDate leaveDate;

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}