package com.hmb.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor // Parametresiz constructor otomatik olarak eklenir
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String messageContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date(); // Mesaj oluşturulurken tarih otomatik olarak atanacak
}