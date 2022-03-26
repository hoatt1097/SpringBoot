package com.thienhoa.blog.controller;

import java.util.List;

import com.thienhoa.blog.model.PaginationResponse;
import com.thienhoa.blog.model.User;
import com.thienhoa.blog.payload.response.ErrorResponse;
import com.thienhoa.blog.repository.UserRepository;
import com.thienhoa.blog.security.CurrentUser;
import com.thienhoa.blog.security.CustomUserDetailsService;
import com.thienhoa.blog.security.UserPrincipal;
import com.thienhoa.blog.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private UserRepository userRepository;
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        try {
            logger.info("************** Get current user START ********************");
            return new ResponseEntity(HttpStatus.OK);
        }
        finally {
            logger.info("************** Get current user stop ********************");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id", required = true) String userId) {
        try {
            logger.info("************** Get user by userId START ********************");
            UserDetails userDetails = customUserDetailsService.loadUserById(Long.parseLong(userId));
            return new ResponseEntity(userDetails, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("User not found!");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        finally {
            logger.info("************** Get user by userId STOP ********************");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<User>> getAllUsers(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int amount) {
        try {
            logger.info("************** Get list user START ********************");
            List<User> userList = userService.getAllUsers();
            return ResponseEntity.ok(new PaginationResponse<>(userList.size(), amount, page, userList));
        }
        catch (Exception e)
        {
            return new ResponseEntity(new ErrorResponse("Get all user false!"), HttpStatus.BAD_REQUEST);
        }
        finally {
            logger.info("************** Get list user STOP ********************");
        }
    }
}
