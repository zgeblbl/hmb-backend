package com.hmb.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hmb.backend.entity.Department;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d WHERE d.isDepartmentDeleted = false")
    List<Department> findAllDepartments();

    @Query("SELECT d FROM Department d WHERE d.departmentId = :id AND d.isDepartmentDeleted = false")
    Optional<Department> findDepartmentById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Department d SET d.departmentName = :departmentName WHERE d.departmentId = :departmentId AND d.isDepartmentDeleted = false")
    int updateDepartment(Long departmentId, String departmentName);

    @Modifying
    @Transactional
    @Query("UPDATE Department d SET d.isDepartmentDeleted = true WHERE d.departmentId = :departmentId")
    int softDeleteDepartment(Long departmentId);
}
