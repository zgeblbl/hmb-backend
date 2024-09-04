package com.hmb.backend.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hmb.backend.entity.Department;
import com.hmb.backend.entity.Title;
import com.hmb.backend.entity.User;
import com.hmb.backend.entity.UserPermission;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.isUserDeleted = false")
    List<User> findAllUsers();
    // List<User> findByIsUserDeletedFalse(); - Auto-generated query to find all not-deleted users

    @Query("SELECT u FROM User u WHERE u.isUserDeleted = false")
    Optional<User> findUserById(Long id);
    // Optional<User> findByIdAndIsUserDeletedFalse(Long id); - Auto-generated query to find a user by ID if not deleted

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.userId = :userId AND u.isUserDeleted = false")
    int updatePassword(Long userId, String newPassword);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.TCKN = :TCKN, u.email = :email, " +
           "u.department = :department, u.title = :title WHERE u.userId = :userId AND u.isUserDeleted = false")
    int updateUser(Long userId, String firstName, String lastName, Long TCKN, String email, Department department, Title title);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isUserDeleted = true WHERE u.userId = :userId")
    int softDeleteUser(Long userId);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isUserDeleted = false")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.isUserDeleted = false")
    Optional<User> authenticateUser(String email, String password);

    @Query("SELECT up FROM UserPermission up WHERE up.user.userId = :userId")
    List<UserPermission> getUserPermissions(Long userId);
}
