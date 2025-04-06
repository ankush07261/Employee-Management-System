package emp.manage.employee.service;

import emp.manage.employee.entity.Admin;
import emp.manage.employee.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin register(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public Admin login(String username, String password) {
        Admin existing = adminRepository.findByUsername(username);
        if (existing != null && passwordEncoder.matches(password, existing.getPassword())) {
            return existing;
        }
        throw new RuntimeException("Invalid credentials");
    }
}
