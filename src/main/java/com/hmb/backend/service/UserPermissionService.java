package com.hmb.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hmb.backend.entity.UserPermission;
import com.hmb.backend.repository.UserPermissionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserPermissionService {

    @Autowired
    private final UserPermissionRepository userPermissionRepository;

    public List<UserPermission> getPermissionsByUserId(Long userId) {
        return userPermissionRepository.findPermissionsByUserId(userId);
    }

    public ResponseEntity<UserPermission> addPermission(UserPermission newPermission) {
        UserPermission createdPermission = userPermissionRepository.save(newPermission);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getPermission(Long permissionId) {
        Optional<UserPermission> permission = userPermissionRepository.findByIdAndNotDeleted(permissionId);

        if (!permission.isPresent()) {
            return new ResponseEntity<>("Permission not found!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(permission.get(), HttpStatus.OK);
    }

    public ResponseEntity<?> updatePermission(UserPermission updatedPermission, Long permissionId) {
        Optional<UserPermission> existingPermission = userPermissionRepository.findByIdAndNotDeleted(permissionId);

        if (!existingPermission.isPresent()) {
            return new ResponseEntity<>("Permission not found!", HttpStatus.NOT_FOUND);
        }

        int rowsAffected = userPermissionRepository.updatePermission(
            permissionId, 
            updatedPermission.getStartDate(), 
            updatedPermission.getEndDate(), 
            updatedPermission.getPermissionType(), 
            updatedPermission.isPermissionApproved()
        );

        if (rowsAffected == 0) {
            return new ResponseEntity<>("Permission update failed!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> approvePermission(Long permissionId) {
        int rowsAffected = userPermissionRepository.approvePermission(permissionId);

        if (rowsAffected == 0) {
            return new ResponseEntity<>("Permission not found or already approved!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> softDeletePermission(Long permissionId) {
        int rowsAffected = userPermissionRepository.softDeletePermission(permissionId);

        if (rowsAffected == 0) {
            return new ResponseEntity<>("Permission not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
