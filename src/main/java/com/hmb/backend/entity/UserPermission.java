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

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "user_permission_id")
    private Long userPermissionId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "is_permission_approved")
    private boolean isPermissionApproved;

    @Column(name = "approval_date")
    private Date approvalDate;

    @Column(name = "is_permission_deleted")
    private boolean isPermissionDeleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", insertable = true, updatable = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_type")
    private PermissionType permissionType;
    public enum PermissionType {
        ANNUAL,
        SICK,
        MATERNITY,
        EXCUSE_LEAVE
    }
}
