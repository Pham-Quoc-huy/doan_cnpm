package com.docpet.animalhospital.web.rest;

import com.docpet.animalhospital.service.ChatService;
import com.docpet.animalhospital.service.dto.ChatRequestDTO;
import com.docpet.animalhospital.service.dto.ChatResponseDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller cho Chat API (Anonymous - không cần đăng nhập)
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private static final Logger LOG = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * POST /api/chat/public/messages
     * Endpoint cho anonymous chat (không cần authentication)
     */
    @PostMapping("/public/messages")
    public ResponseEntity<ChatResponseDTO> sendMessage(@Valid @RequestBody ChatRequestDTO request) {
        LOG.info("REST request to send anonymous chat message");
        
        ChatResponseDTO response = chatService.processMessage(request);
        return ResponseEntity.ok(response);
    }
}

