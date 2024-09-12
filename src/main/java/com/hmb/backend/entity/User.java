package com.hmb.backend.entity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "tckn")
    private Long TCKN;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private boolean isUserDeleted= false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty("department")
    @JoinColumn(name = "department_id", insertable = true, updatable = true)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty("title")
    @JoinColumn(name = "title_id", insertable = true, updatable = true)
    private Title title;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserPermission> userPermissions;
}
