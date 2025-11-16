package com.agrotrack.agrotrack.service;

import com.agrotrack.agrotrack.dto.AuthResponse;
import com.agrotrack.agrotrack.dto.LoginRequest;
import com.agrotrack.agrotrack.dto.RegisterRequest;
import com.agrotrack.agrotrack.entity.User;
import com.agrotrack.agrotrack.repository.UserRepository;
import com.agrotrack.agrotrack.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return new AuthResponse(null, null, "Erreur: Ce nom d'utilisateur existe déjà");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new AuthResponse(null, null, "Erreur: Cet email est déjà utilisé");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER");

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), "Inscription réussie");
    }

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            return new AuthResponse(token, loginRequest.getUsername(), "Connexion réussie");
        } catch (Exception e) {
            return new AuthResponse(null, null, "Erreur: Identifiants invalides");
        }
    }

    public Optional<User> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                if (!username.equals("anonymousUser")) {
                    return userRepository.findByUsername(username);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Long getCurrentUserId() {
        try {
            Optional<User> user = getCurrentUser();
            return user.map(User::getId).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}