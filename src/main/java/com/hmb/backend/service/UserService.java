package com.hmb.backend.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.hmb.backend.entity.User;
import com.hmb.backend.entity.UserPermission;
import com.hmb.backend.repository.UserRepository;
import com.hmb.backend.repository.UserPermissionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    
    @Autowired
    private final UserPermissionRepository userPermissionRepository;

    @Autowired


    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findUserById(id);
    }

    public User addUser(User newUser) {
        newUser.setPassword(newUser.getPassword());
        return userRepository.save(newUser);
    }

    public ResponseEntity<?> updateUser(User updatedUser, Long id) {
        int rowsAffected = userRepository.updateUser(
            id,
            updatedUser.getFirstName(),
            updatedUser.getLastName(),
            updatedUser.getTCKN(),
            updatedUser.getEmail(),
            updatedUser.getDepartment(),
            updatedUser.getTitle()
        );

        if (rowsAffected == 0) {
            return new ResponseEntity<>("User not found or is deleted!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> updatePassword(Long id, String newPassword) {
        int rowsAffected = userRepository.updatePassword(id, newPassword);

        if (rowsAffected == 0) {
            return new ResponseEntity<>("User not found or is deleted!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> removeUser(Long id) {
        int rowsAffected = userRepository.softDeleteUser(id);

        if (rowsAffected == 0) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findUserByEmail(email);
    
        if (user.isEmpty() || !password.matches(user.get().getPassword())) {
            return new ResponseEntity<>("Invalid email or password!", HttpStatus.UNAUTHORIZED);
        }
    
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
    

    public ResponseEntity<?> getUserPermissions(Long userId) {
        List<UserPermission> permissions = userRepository.getUserPermissions(userId);

        if (permissions.isEmpty()) {
            return new ResponseEntity<>("No permissions found for this user!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    public ResponseEntity<?> addPermissionToUser(Long userId, UserPermission newPermission) {
        Optional<User> user = userRepository.findUserById(userId);

        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        if (user.get().isUserDeleted()) {
            return new ResponseEntity<>("User is deleted!", HttpStatus.BAD_REQUEST);
        }

        newPermission.setUser(user.get());
        UserPermission createdPermission = userPermissionRepository.save(newPermission);

        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    public String encryptPassword(String password) {
        return "";
    }
}
