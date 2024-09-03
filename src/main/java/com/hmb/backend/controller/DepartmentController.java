package com.hmb.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmb.backend.entity.Department;
import com.hmb.backend.service.DepartmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/hmb/departments")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DepartmentController {
    
    
    @Autowired
    private final DepartmentService departmentService;

    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @PostMapping("/addDepartment")
    public ResponseEntity<Department> addDepartment(@RequestBody Department newDepartment) {
        Department createdDepartment = departmentService.addDepartment(newDepartment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @GetMapping("/getDepartment/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartment(id);

        if (department.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found!");
        }

        return ResponseEntity.ok(department.get());
    }

    @PutMapping("/updateDepartment/{id}")
    public ResponseEntity<?> updateDepartment(@RequestBody Department updatedDepartment, @PathVariable Long id) {
        return departmentService.updateDepartment(updatedDepartment, id);
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<?> removeDepartment(@PathVariable Long id) {
        return departmentService.removeDepartment(id);
    }
}
