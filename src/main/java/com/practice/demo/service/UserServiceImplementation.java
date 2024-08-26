package com.practice.demo.service;

import com.practice.demo.config.JwtProvider;
import com.practice.demo.entitiy.CustomeUserDetails;
import com.practice.demo.entitiy.Role;
import com.practice.demo.entitiy.RoleEnum;
import com.practice.demo.entitiy.User;
import com.practice.demo.repository.RoleRepository;
import com.practice.demo.repository.UserRepository;
import com.practice.demo.request.CreateRequest;
import com.practice.demo.request.LoginRequest;
import com.practice.demo.response.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(CreateRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        RoleEnum roleEnum = RoleEnum.valueOf(request.getRole());
        Role role = roleRepository.findByName(roleEnum).orElseThrow(()-> new RuntimeException("Role " + roleEnum + " not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        return userRepository.save(user);
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//            );
//
//            String token = jwtProvider.generateToken(authentication);
//            return new AuthResponse(token);
//        }
//        catch (AuthenticationException e) {
//            throw new RuntimeException("Invalid email or password", e);
//        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                CustomeUserDetails customeUserDetails = new CustomeUserDetails(user);
                String userRole = String.valueOf(user.getRole().getName());
                String token = jwtProvider.generateToken(customeUserDetails, userRole);
                System.out.println(token);
                return new AuthResponse(token);
            }
        }
        throw new UsernameNotFoundException("Invalid email or password");
    }
}
