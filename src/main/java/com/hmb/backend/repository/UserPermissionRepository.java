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

    @Query("SELECT up FROM UserPermission up WHERE up.userPermissionId = :permissionId AND up.isPermissionDeleted = false")
    Optional<UserPermission> findByIdAndNotDeleted(Long permissionId);

    @Modifying
    @Transactional
    @Query("UPDATE UserPermission up SET up.startDate = :startDate, up.endDate = :endDate, up.permissionType = :permissionType, up.isPermissionApproved = :isPermissionApproved WHERE up.userPermissionId = :permissionId AND up.isPermissionDeleted = false")
    int updatePermission(Long permissionId, Date startDate, Date endDate, UserPermission.PermissionType permissionType, boolean isPermissionApproved);

    @Modifying
    @Transactional
    @Query("UPDATE UserPermission up SET up.isPermissionApproved = true, up.approvalDate = CURRENT_DATE WHERE up.userPermissionId = :permissionId AND up.isPermissionDeleted = false")
    int approvePermission(Long permissionId);

    @Modifying
    @Transactional
    @Query("UPDATE UserPermission up SET up.isPermissionDeleted = true WHERE up.userPermissionId = :permissionId")
    int softDeletePermission(Long permissionId);
}
