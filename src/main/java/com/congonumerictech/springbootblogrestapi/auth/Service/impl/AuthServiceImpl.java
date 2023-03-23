package com.congonumerictech.springbootblogrestapi.auth.Service.impl;

import com.congonumerictech.springbootblogrestapi.auth.Service.AuthService;
import com.congonumerictech.springbootblogrestapi.auth.dto.LoginDto;
import com.congonumerictech.springbootblogrestapi.auth.dto.RegisterDto;
import com.congonumerictech.springbootblogrestapi.auth.entity.Role;
import com.congonumerictech.springbootblogrestapi.auth.entity.User;
import com.congonumerictech.springbootblogrestapi.auth.repository.RoleRepository;
import com.congonumerictech.springbootblogrestapi.auth.repository.UserRepository;
import com.congonumerictech.springbootblogrestapi.auth.security.JwtTokenProvider;
import com.congonumerictech.springbootblogrestapi.exception.BlogAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           JwtTokenProvider jwtTokenProvider)
    {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUserNameOrEmail(),
                        loginDto.getPassword()));
        // store in security context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateJwtToken(authentication);

        return jwtToken;
    }

    @Override
    public String register(RegisterDto registerDto) {

        checkIfEmailAlreadyExist(registerDto.getEmail());
        checkIfUsernameAlreadyExist(registerDto.getUserName());

        User newUser = new User();
        newUser.setName(registerDto.getName());
        newUser.setUserName(registerDto.getUserName());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Optional<Role> roleUser = roleRepository.findByName("ROLE_USER");
        roleUser.ifPresent(roles::add);

        newUser.setRoles(roles);

        userRepository.save(newUser);

        return "Successfully register User!";
    }

    private void checkIfEmailAlreadyExist(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "This email already exist");
        }
    }

    private void checkIfUsernameAlreadyExist(String username) {
        if(userRepository.existsByUserName(username)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "This Username already exist");
        }
    }
}
