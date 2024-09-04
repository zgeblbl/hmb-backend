package com.hmb.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hmb.backend.entity.Department;
import com.hmb.backend.repository.DepartmentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentService {
    @Autowired
    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAllDepartments();
    }

    public Optional<Department> getDepartment(Long id) {
        return departmentRepository.findDepartmentById(id);
    }

    public Department addDepartment(Department newDepartment) {
        return departmentRepository.save(newDepartment);
    }

    public ResponseEntity<?> updateDepartment(Department updatedDepartment, Long id) {
        int rowsAffected = departmentRepository.updateDepartment(id, updatedDepartment.getDepartmentName());

        if (rowsAffected == 0) {
            return new ResponseEntity<>("Department not found or is deleted!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> removeDepartment(Long id) {
        int rowsAffected = departmentRepository.softDeleteDepartment(id);

        if (rowsAffected == 0) {
            return new ResponseEntity<>("Department not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
