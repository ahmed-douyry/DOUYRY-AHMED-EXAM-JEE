package com.example.douyry_ahmed.web;

import com.example.douyry_ahmed.dtos.AuthRequest;
import com.example.douyry_ahmed.dtos.AuthResponse;
import com.example.douyry_ahmed.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        log.info("POST /auth/login reçu pour utilisateur={}", authRequest != null ? authRequest.getUsername() : null);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        return new AuthResponse(jwtService.generateToken(authentication));
    }
}
