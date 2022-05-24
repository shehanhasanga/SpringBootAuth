package com.shehan.blog.blogApp.controller;

import com.shehan.blog.blogApp.model.Role;
import com.shehan.blog.blogApp.model.SignupDto;
import com.shehan.blog.blogApp.model.User;
import com.shehan.blog.blogApp.payload.LoginDto;
import com.shehan.blog.blogApp.repository.RoleRepository;
import com.shehan.blog.blogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  new ResponseEntity<>("User sign in successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupDto signupDto){
        if(userRepository.existsByUsername(signupDto.getUsername())){
            return  new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signupDto.getEmail())){
            return  new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setPassword(signupDto.getPassword());
        user.setEmail(signupDto.getEmail());

        Role roles = roleRepository.findByName("ROLW_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<String >("User is registered successfully", HttpStatus.OK);
    }
}
