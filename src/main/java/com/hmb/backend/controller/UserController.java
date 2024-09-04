package com.hmb.backend.controller;

import java.util.List;
import java.util.Optional;

import com.hmb.backend.controller.dto.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmb.backend.entity.User;
import com.hmb.backend.entity.UserPermission;
import com.hmb.backend.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/hmb/users")
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {
   
    
    @Autowired
    private final UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserRequestDTO loginRequest) {
        return userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
    }


    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User newUser) {
        User createdUser = userService.addUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        return ResponseEntity.ok(user.get());
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
        return userService.updateUser(updatedUser, id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> removeUser(@PathVariable Long id) {
        return userService.removeUser(id);
    }

    @GetMapping("/getUserPermissions/{id}")
    public ResponseEntity<?> getUserPermissions(@PathVariable Long id) {
        return userService.getUserPermissions(id);
    }

    @PostMapping("/addPermissionToUser/{id}")
    public ResponseEntity<?> addPermissionToUser(@PathVariable Long id, @RequestBody UserPermission newPermission) {
        return userService.addPermissionToUser(id, newPermission);
    }
}
