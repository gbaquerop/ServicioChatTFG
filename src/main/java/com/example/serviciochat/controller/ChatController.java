package com.example.serviciochat.controller;

import com.example.serviciochat.DTO.MessageDTO;
import com.example.serviciochat.DTO.NuevoChatDTO;
import com.example.serviciochat.model.Chat;
import com.example.serviciochat.model.Message;
import com.example.serviciochat.service.IChatService;
import com.example.serviciochat.service.IMessageService;
import com.example.serviciochat.utils.EncriptationHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/chat")
public class ChatController {
    private IChatService chatService;

    public ChatController(IChatService chatService,
                          IMessageService messageService) {
        this.chatService = chatService;
    }

    @GetMapping("/DameMisChats/{id}")
    public ResponseEntity<List<NuevoChatDTO>> DameMisChats(@PathVariable String id) {
        List<NuevoChatDTO> allChatsBUserId = chatService.findAllChatsBUserId(id);
        return ResponseEntity.ok(allChatsBUserId);
    }

    @GetMapping("/CrearChat/{idUsuario1}/{idUsuario2}")
    public ResponseEntity<NuevoChatDTO> CrearChat(@PathVariable String idUsuario1, @PathVariable String idUsuario2) {
        Optional<NuevoChatDTO> chat = chatService.crearChat(idUsuario1, idUsuario2);
        if (chat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chat.get());
    }

    @GetMapping("/DameMensajes/{idUsuario1}/{idUsuario2}")
    public ResponseEntity<List<MessageDTO>> DameMensajes(@PathVariable String idUsuario1,
                                                         @PathVariable String idUsuario2) {
        List<MessageDTO> messages = chatService.getAllMessages(idUsuario1, idUsuario2);
        return ResponseEntity.ok(messages);
    }

}
