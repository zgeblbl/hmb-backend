package com.hmb.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hmb.backend.entity.Title;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    @Query("SELECT t FROM Title t WHERE t.isTitleDeleted = false")
    List<Title> findAllTitles();

    @Query("SELECT t FROM Title t WHERE t.titleId = :id AND t.isTitleDeleted = false")
    Optional<Title> findTitleById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Title t SET t.titleName = :titleName WHERE t.titleId = :titleId AND t.isTitleDeleted = false")
    int updateTitle(Long titleId, String titleName);

    @Modifying
    @Transactional
    @Query("UPDATE Title t SET t.isTitleDeleted = true WHERE t.titleId = :titleId")
    int softDeleteTitle(Long titleId);
}
