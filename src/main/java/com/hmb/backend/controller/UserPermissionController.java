package com.hmb.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmb.backend.entity.UserPermission;
import com.hmb.backend.service.UserPermissionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/hmb/permissions")
@CrossOrigin(origins = "*")
public class UserPermissionController {
    
    
    @Autowired
    private final UserPermissionService userPermissionService;

    @GetMapping("/getUserPermissions/{userId}")
    public ResponseEntity<List<UserPermission>> getPermissionsByUserId(@PathVariable Long userId) {
        List<UserPermission> permissions = userPermissionService.getPermissionsByUserId(userId);
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }
    @GetMapping("/getDepartmentPermissions/{departmentId}")
    public ResponseEntity<List<UserPermission>> getPermissionsByDepartmentId(@PathVariable Long departmentId) {
        try {
            List<UserPermission> permissions = userPermissionService.getPermissionsByDepartmentId(departmentId);
            return new ResponseEntity<>(permissions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPermission/{id}")
    public ResponseEntity<?> getPermission(@PathVariable Long id) {
        return userPermissionService.getPermission(id);
    }

    @PostMapping("/addPermission")
    public ResponseEntity<UserPermission> addPermission(@RequestBody UserPermission newPermission) {
        return userPermissionService.addPermission(newPermission);
    }

    @PutMapping("/updatePermission/{id}")
    public ResponseEntity<?> updatePermission(@RequestBody UserPermission updatedPermission, @PathVariable Long id) {
        return userPermissionService.updatePermission(updatedPermission, id);
    }

    @PatchMapping("/approvePermission/{id}")
    public ResponseEntity<?> approvePermission(@PathVariable Long id) {
        return userPermissionService.approvePermission(id);
    }

    @PatchMapping("/declinePermission/{id}")
    public ResponseEntity<?> declinePermission(@PathVariable Long id) {
        return userPermissionService.declinePermission(id);
    }
}
