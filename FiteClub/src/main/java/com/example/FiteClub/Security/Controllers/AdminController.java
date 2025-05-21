package com.example.FiteClub.Security.Controllers;

import com.example.FiteClub.Security.DTO.DeleteUserRequest;
import com.example.FiteClub.Security.DTO.RegisterDto;
import com.example.FiteClub.Security.Repositories.RoleRepository;
import com.example.FiteClub.Security.Repositories.UserRepository;
import com.example.FiteClub.Security.Role;
import com.example.FiteClub.Security.UserPackage.UpdateUser;
import com.example.FiteClub.Security.UserPackage.UserEntity;
import com.example.FiteClub.Services.CardService;
import com.example.FiteClub.repos.CardRepo;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/admin")
@Transactional
public class AdminController {

    @Autowired
    private CardService cardService;
    @Autowired
    private CardRepo cardRepo;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete-user")
    @Transactional
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequest request) {
        String username = request.getUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (user.getCard() != null) {
            Long cardId = user.getCard().getId();
            user.setCard(null);
            cardRepo.deleteById(cardId);
        }

        user.getRoles().clear();

        userRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully!");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser (@RequestBody UpdateUser request) {
        String username = request.getUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (request.getNewUsername() != null) {
            user.setUsername(request.getNewUsername());
        }
        if (request.getNewPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully!");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add-user")
    public ResponseEntity<String> addUser (@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username is taken!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        String roleName = registerDto.getRole();
        Role role;

        if (roleName != null && (roleName.equalsIgnoreCase("ROLE_ADMIN") || roleName.equalsIgnoreCase("ROLE_USER"))) {
            role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));
        } else {
            return ResponseEntity.badRequest().body("Invalid role specified!");
        }

        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return ResponseEntity.ok("User  created successfully!");
    }
//    public ResponseEntity<?> addCategory(@RequestBody ){
//
//    }
}