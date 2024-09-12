package com.hmb.backend.service;

import com.hmb.backend.entity.Message;
import com.hmb.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    // Tüm mesajları al
    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages.isEmpty() ? new ArrayList<>() : messages;
    }

}