package com.enotes.config.init;

import com.enotes.entity.AccountStatus;
import com.enotes.entity.Role;
import com.enotes.entity.User;
import com.enotes.repository.RoleRepository;
import com.enotes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        log.info("🔁 Running DataInitializer...");

        // 1. Ensure roles exist
        createRoleIfMissing("ADMIN");
        createRoleIfMissing("USER");

        // 2. Check and create admin
        User existingAdmin = userRepository.findByEmail(adminEmail);
        if (existingAdmin == null) {
            Optional<Role> adminRole = roleRepository.findByName("ADMIN");
            if (adminRole.isPresent()) {
                User admin = new User();
                admin.setFirstName("Super");
                admin.setLastName("Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRoles(Set.of(adminRole.get()));
                admin.setStatus(AccountStatus.builder()
                        .isActive(true)
                        .verificationCode(null)
                        .build());

                userRepository.save(admin);
                log.info("✅ Default admin user created: {}", adminEmail);
            } else {
                log.error("❌ ROLE_ADMIN not found. Admin user not created.");
            }
        } else {
            log.info("ℹ️ Admin already exists: {}", adminEmail);
        }

        log.info("✅ DataInitializer completed.");
    }

    private void createRoleIfMissing(String roleName) {
        Optional<Role> existing = roleRepository.findByName(roleName);
        if (existing.isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            log.info("✅ Created missing role: {}", roleName);
        }
    }
}
