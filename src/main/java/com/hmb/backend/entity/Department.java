package com.hmb.backend.entity;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;
    
    @Column(name = "department")
    private String departmentName;

    @Column(name = "deleted")
    private boolean isDepartmentDeleted;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<User> users;
}
