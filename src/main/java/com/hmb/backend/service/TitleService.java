package com.hmb.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hmb.backend.entity.Title;
import com.hmb.backend.repository.TitleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TitleService {
    @Autowired
    private final TitleRepository titleRepository;

    public List<Title> getAllTitles() {
        return titleRepository.findAllTitles();
    }

    public Optional<Title> getTitle(Long id) {
        return titleRepository.findTitleById(id);
    }

    public Title addTitle(Title newTitle) {
        return titleRepository.save(newTitle);
    }

    public ResponseEntity<?> updateTitle(Title updatedTitle, Long id) {
        int rowsAffected = titleRepository.updateTitle(id, updatedTitle.getTitleName());

        if (rowsAffected == 0) {
            return new ResponseEntity<>("Title not found or is deleted!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> removeTitle(Long id) {
        int rowsAffected = titleRepository.softDeleteTitle(id);

        if (rowsAffected == 0) {
            return new ResponseEntity<>("Title not found!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
