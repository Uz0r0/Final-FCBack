package com.example.FiteClub.Security;

import com.example.FiteClub.Security.Repositories.RoleRepository;
import com.example.FiteClub.Security.Repositories.UserRepository;
import com.example.FiteClub.Security.UserPackage.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminSeeder implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.existsByUsername("admin")) {
            System.out.println("Admin already exists, no need to create it.");
            return;
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin12345"));
        admin.getRoles().add(adminRole);

        userRepository.save(admin);
        System.out.println("Admin created successfully.");
    }

}
