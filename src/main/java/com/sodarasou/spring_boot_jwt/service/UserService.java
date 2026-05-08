package com.sodarasou.spring_boot_jwt.service;

import com.sodarasou.spring_boot_jwt.entity.Role;
import com.sodarasou.spring_boot_jwt.entity.User;
import com.sodarasou.spring_boot_jwt.repository.RoleRepository;
import com.sodarasou.spring_boot_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User registerUser(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role defaultRole = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new IllegalStateException("Default role 'EMPLOYEE' not found in database."));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        user.getRoles().add(defaultRole);

        return userRepository.save(user);
    }

}
