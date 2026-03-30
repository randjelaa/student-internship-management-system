package com.example.internships.controller;

import com.example.internships.dto.auth.LoginRequest;
import com.example.internships.dto.auth.LoginResponse;
import com.example.internships.entity.User;
import com.example.internships.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        httpRequest.getSession(true)
                .setAttribute(
                        "SPRING_SECURITY_CONTEXT",
                        SecurityContextHolder.getContext()
                );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        if (principal == null) {
            throw new RuntimeException("Authentication failed");
        }

        User user = principal.getUser();

        if (!user.getActive()) {
            throw new RuntimeException("Authentication failed");
        }

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public LoginResponse me(Authentication authentication) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        assert principal != null;
        return new LoginResponse(
                principal.getUser().getId(),
                principal.getUser().getEmail(),
                principal.getUser().getRole().name()
        );
    }
}