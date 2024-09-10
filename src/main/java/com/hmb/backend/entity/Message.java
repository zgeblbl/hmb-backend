package com.hmb.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String messageContent;

    public Message() {}

    public Message(String name, String email, String messageContent) {
        this.name = name;
        this.email = email;
        this.messageContent = messageContent;
    }

    // Getters and Setters
}
