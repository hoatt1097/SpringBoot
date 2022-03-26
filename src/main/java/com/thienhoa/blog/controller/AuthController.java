package com.thienhoa.blog.controller;

import com.thienhoa.blog.exception.AppException;
import com.thienhoa.blog.model.Role;
import com.thienhoa.blog.type.RoleName;
import com.thienhoa.blog.model.User;
import com.thienhoa.blog.payload.response.ErrorResponse;
import com.thienhoa.blog.payload.response.JwtAuthenticationResponse;
import com.thienhoa.blog.payload.request.LoginRequest;
import com.thienhoa.blog.payload.request.SignUpRequest;
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            logger.info("************** Sign in START ********************");
            // UserDetails user = customUserDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return new ResponseEntity(new JwtAuthenticationResponse(jwt), HttpStatus.OK);
        }
        catch (UsernameNotFoundException e){
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        finally {
            logger.info("************** Sign in STOP ********************");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        try {
            logger.info("************** Register START ********************");
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return new ResponseEntity(new ErrorResponse("Username is already taken!"), HttpStatus.BAD_REQUEST);
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return new ResponseEntity(new ErrorResponse("Email Address already in use!"), HttpStatus.BAD_REQUEST);
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

            return new ResponseEntity(HttpStatus.OK);
        }
        finally {
            logger.info("************** Register FINISH ********************");
        }
    }
}
