package com.hmb.backend.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hmb.backend.entity.UserPermission;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {

    @Query("SELECT up FROM UserPermission up WHERE up.user.userId = :userId AND up.isPermissionDeleted = false")
    List<UserPermission> findPermissionsByUserId(Long userId);


    @Query("SELECT up FROM UserPermission up WHERE up.user.department.departmentId = :departmentId AND up.isPermissionDeleted = false AND up.permissionApproval = 'PENDING'")
    List<UserPermission> findPermissionsByDepartmentId(Long departmentId);

    @Query("SELECT up FROM UserPermission up WHERE up.userPermissionId = :permissionId AND up.isPermissionDeleted = false")
    Optional<UserPermission> findByIdAndNotDeleted(Long permissionId);

    @Modifying
    @Transactional
    @Query("UPDATE UserPermission up SET up.startDate = :startDate, up.endDate = :endDate, up.permissionType = :permissionType, up.permissionApproval = :permissionApproval WHERE up.userPermissionId = :permissionId AND up.isPermissionDeleted = false")
    int updatePermission(Long permissionId, Date startDate, Date endDate, UserPermission.PermissionType permissionType, UserPermission.PermissionApproval permissionApproval);

    @Modifying
    @Transactional
    @Query("UPDATE UserPermission up SET up.permissionApproval = 'APPROVED', up.approvalDate = CURRENT_DATE WHERE up.userPermissionId = :permissionId AND up.isPermissionDeleted = false")
    int approvePermission(Long permissionId);

    @Modifying
    @Transactional
    @Query("UPDATE UserPermission up SET up.permissionApproval = 'DECLINED', up.approvalDate = CURRENT_DATE WHERE up.userPermissionId = :permissionId AND up.isPermissionDeleted = false")
    int declinePermission(Long permissionId);

    @Modifying
    @Transactional
    @Query("UPDATE UserPermission up SET up.isPermissionDeleted = true WHERE up.userPermissionId = :permissionId")
    int softDeletePermission(Long permissionId);
}
