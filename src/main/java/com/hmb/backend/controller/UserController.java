package com.hmb.backend.controller;

import java.util.List;
import java.util.Map;
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

    @PostMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestBody Map<String, Object> searchParams) {
        System.out.println("Incoming Request: " + searchParams);
        Long TCKN = null;

        // TCKN'nin boş olup olmadığını kontrol et
        if (searchParams.containsKey("TCKN") && !searchParams.get("TCKN").toString().isEmpty()) {
            TCKN = Long.valueOf(searchParams.get("TCKN").toString());
        }

        // firstName kontrolü
        String firstName = (searchParams.get("firstName") != null && !searchParams.get("firstName").toString().isEmpty())
                ? searchParams.get("firstName").toString()
                : null;

        // lastName kontrolü
        String lastName = (searchParams.get("lastName") != null && !searchParams.get("lastName").toString().isEmpty())
                ? searchParams.get("lastName").toString()
                : null;

        // Kullanıcıları bulma
        List<User> users = userService.searchUsers(TCKN, firstName, lastName);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

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

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody Map<String, String> passwords) {
        String currentPassword = passwords.get("currentPassword");
        String newPassword = passwords.get("newPassword");

        return userService.changeUserPassword(id, currentPassword, newPassword);
    }

}