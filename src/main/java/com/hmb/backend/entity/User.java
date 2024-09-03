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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId; 

    @Column(name = "name")
    private String firstName;

    @Column(name = "surname")
    private String lastName;

    @Column(name = "TCKN")
    private Long TCKN;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "deleted")
    private boolean isUserDeleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", insertable = true, updatable = true)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "title_id", insertable = true, updatable = true)
    private Title title;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPermission> userPermissions;
}
