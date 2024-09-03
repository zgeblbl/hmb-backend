package com.hmb.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmb.backend.entity.Title;
import com.hmb.backend.service.TitleService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/hmb/titles")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TitleController {
    
    @Autowired
    private final TitleService titleService;

    @GetMapping("/getAllTitles")
    public ResponseEntity<List<Title>> getAllTitles() {
        List<Title> titles = titleService.getAllTitles();
        return ResponseEntity.ok(titles);
    }

    @PostMapping("/addTitle")
    public ResponseEntity<Title> addTitle(@RequestBody Title newTitle) {
        Title createdTitle = titleService.addTitle(newTitle);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTitle);
    }

    @GetMapping("/getTitle/{id}")
    public ResponseEntity<?> getTitle(@PathVariable Long id) {
        Optional<Title> title = titleService.getTitle(id);

        if (title.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Title not found!");
        }

        return ResponseEntity.ok(title.get());
    }

    @PutMapping("/updateTitle/{id}")
    public ResponseEntity<?> updateTitle(@RequestBody Title updatedTitle, @PathVariable Long id) {
        return titleService.updateTitle(updatedTitle, id);
    }

    @DeleteMapping("/deleteTitle/{id}")
    public ResponseEntity<?> removeTitle(@PathVariable Long id) {
        return titleService.removeTitle(id);
    }
}
