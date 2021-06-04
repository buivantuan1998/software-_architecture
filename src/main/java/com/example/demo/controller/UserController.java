package com.example.demo.controller;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.entities.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.security.Jwt.JwtProvider;
import com.example.demo.service.impl.CustomerUserDetails;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirst_name());
        user.setLastName(request.getLast_name());
        user.setRole(roleRepository.findRoleName("USER"));
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomerUserDetails user = (CustomerUserDetails) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(user.getUsername());
        return ResponseEntity.ok("token: " + jwt);
    }

    @GetMapping("/user/get_list")
    public ResponseEntity<Iterable<User>> getListUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/user/get_user_by_id")
    public ResponseEntity<User> getUserById(@RequestParam Long id) {
        Optional<User> getOptionalUser = userService.findById(id);
        return getOptionalUser.map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/insert")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestParam Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(user1 -> {
            user.setId(user1.getId());
            return ResponseEntity.ok(userService.save(user));
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(user -> {
            userService.remove(id);
            return ResponseEntity.ok("");
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
