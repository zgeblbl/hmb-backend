package com.hmb.backend.entity;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permissions")
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long userPermissionId;

    @Column(name = "permission_start")
    private Date startDate;

    @Column(name = "permission_end")
    private Date endDate;

    @Column(name = "approved")
    private boolean isPermissionApproved;

    @Column(name = "approval_date")
    private Date approvalDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_type")
    private PermissionType permissionType;

    @Column(name = "deleted")
    private boolean isPermissionDeleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", insertable = true, updatable = true)
    private User user;
    
    public enum PermissionType {
        ANNUAL,
        COMPASSIONATE,
        SICK
    }
}
