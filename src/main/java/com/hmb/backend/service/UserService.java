package com.hmb.backend.service;
import java.util.List;
import java.util.Optional;

import com.hmb.backend.entity.Department;
import com.hmb.backend.entity.Title;
import com.hmb.backend.repository.DepartmentRepository;
import com.hmb.backend.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.hmb.backend.entity.User;
import com.hmb.backend.entity.UserPermission;
import com.hmb.backend.repository.UserRepository;
import com.hmb.backend.repository.UserPermissionRepository;
import com.hmb.backend.controller.dto.UserResponseDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    private final TitleRepository titleRepository;

    @Autowired
    private final UserPermissionRepository userPermissionRepository;

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findUserById(id);
    }

    public User addUser(User newUser) {
        try {
            // Fetch the Department and Title entities by ID
            Department department = departmentRepository.findById(newUser.getDepartment().getDepartmentId()).orElse(null);
            Title title = titleRepository.findById(newUser.getTitle().getTitleId()).orElse(null);

            // Ensure Department and Title are found
            if (department == null || title == null) {
                throw new IllegalArgumentException("Invalid department or title.");
            }

            // Set the fetched Department and Title to the newUser
            newUser.setDepartment(department);
            newUser.setTitle(title);

            // Optionally handle password hashing
            newUser.setPassword(newUser.getPassword());
            newUser.getDepartment().setDepartmentId(newUser.getDepartment().getDepartmentId());

            // Save the user
            return userRepository.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // Handle the exception as needed
        }
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
    public ResponseEntity<?> changeUserPassword(Long userId, String currentPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Mevcut şifre kontrolü
            if (!user.getPassword().equals(currentPassword)) {
                return new ResponseEntity<>("Current password is incorrect!", HttpStatus.BAD_REQUEST);
            }

            // Şifre güncelleme
            user.setPassword(newPassword);  // Şifreyi güncellerken şifreleme eklenebilir
            userRepository.save(user);

            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
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

        if (user.isPresent()) {
            User foundUser = user.get();
            if (password.equals(foundUser.getPassword())) {
                // Kullanıcının adını ve soyadını döndürüyoruz
                UserResponseDTO responseDTO = new UserResponseDTO(foundUser.getFirstName(), foundUser.getLastName(), foundUser.getUserId(), foundUser.getTitle().getTitleId(), foundUser.getDepartment().getDepartmentId());
                return ResponseEntity.ok(responseDTO);  // Yanıtı JSON formatında döndürür
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
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

    public List<User> searchUsers(Long TCKN, String firstName, String lastName) {
        return userRepository.findByQuery(TCKN, firstName, lastName);
    }
}
