package com.hmb.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hmb.backend.entity.User;
import com.hmb.backend.entity.UserPermission;
import com.hmb.backend.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/hmbeimza")
public class DenemeController {

    @PostMapping("/direct-sign-callback")
    public ResponseEntity<String>  directSignCallBack(@RequestBody String directSignCallbackRequestDTO) {
        String deneme = "aaa";
        return ResponseEntity.ok(deneme);
    }
}