package com.thienhoa.blog.controller;

import com.thienhoa.blog.exception.AppException;
import com.thienhoa.blog.model.Role;
import com.thienhoa.blog.type.RoleName;
import com.thienhoa.blog.model.User;
import com.thienhoa.blog.payload.ApiResponse;
import com.thienhoa.blog.payload.JwtAuthenticationResponse;
import com.thienhoa.blog.payload.LoginRequest;
import com.thienhoa.blog.payload.SignUpRequest;
import com.thienhoa.blog.repository.RoleRepository;
import com.thienhoa.blog.repository.UserRepository;
import com.thienhoa.blog.security.CustomUserDetailsService;
import com.thienhoa.blog.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            logger.info("************** Signin START ********************");
            // UserDetails user = customUserDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return new ResponseEntity(new ApiResponse(true, "Successfully!", new JwtAuthenticationResponse(jwt)),
                    HttpStatus.OK);
        }
        catch (UsernameNotFoundException e){
            return new ResponseEntity(new ApiResponse(false, e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(new ApiResponse(false, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        finally {
            logger.info("************** Signin STOP ********************");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        try {
            logger.info("************** Register START ********************");
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                        HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                        HttpStatus.BAD_REQUEST);
            }

            // Creating user's account
            User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                    signUpRequest.getEmail(), signUpRequest.getPassword());

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));

            if(signUpRequest.getRole() != null && signUpRequest.getRole().equals("admin")) {
                userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> new AppException("User Role not set."));
            }
            user.setRoles(Collections.singleton(userRole));

            userRepository.save(user);

            return new ResponseEntity(new ApiResponse(true, "User registered successfully"), HttpStatus.OK);
        }
        finally {
            logger.info("************** Register FINISH ********************");
        }
    }
}
