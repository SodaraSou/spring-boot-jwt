package com.sodarasou.spring_boot_jwt.controller;

import com.sodarasou.spring_boot_jwt.dto.LoginDto;
import com.sodarasou.spring_boot_jwt.dto.RegisterDto;
import com.sodarasou.spring_boot_jwt.entity.User;
import com.sodarasou.spring_boot_jwt.service.JwtService;
import com.sodarasou.spring_boot_jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto registerDto) {
        User newUser = userService.registerUser(registerDto.username(), registerDto.password());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String username = loginDto.username();
        String password = loginDto.password();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}

