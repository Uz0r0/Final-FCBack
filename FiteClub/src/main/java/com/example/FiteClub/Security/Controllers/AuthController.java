package com.example.FiteClub.Security.Controllers;

import com.example.FiteClub.Security.ChangePasswordRequest;
import com.example.FiteClub.Security.DTO.AuthResponseDTO;
import com.example.FiteClub.Security.DTO.LoginDto;
import com.example.FiteClub.Security.DTO.RegisterDto;
import com.example.FiteClub.Security.JWT.JWTGenerator;
import com.example.FiteClub.Security.Repositories.RoleRepository;
import com.example.FiteClub.Security.Repositories.UserRepository;
import com.example.FiteClub.Security.Role;
import com.example.FiteClub.Security.Services.UserService;
import com.example.FiteClub.Security.UserPackage.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JWTGenerator jwtGenerator,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            String refreshToken = jwtGenerator.generateRefreshToken(authentication);
            return new ResponseEntity<>(new AuthResponseDTO(token, refreshToken, "NONE"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponseDTO(null, null, "Invalid username or password"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        Role role = roleRepository.findByName("ROLE_USER").orElse(null);
        if (role == null) {
            return new ResponseEntity<>("Role USER not found!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.getRoles().add(role);

        userRepository.save(user);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(registerDto.getUsername(), registerDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            String refreshToken = jwtGenerator.generateRefreshToken(authentication);
            return new ResponseEntity<>(new AuthResponseDTO(token, refreshToken, "NONE"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponseDTO(null, null, "Invalid username or password"), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        UserEntity user = userService.getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return new ResponseEntity<>("Current password is incorrect!", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
    }
}
