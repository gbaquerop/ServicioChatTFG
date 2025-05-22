package com.example.serviciochat.service;

import com.example.serviciochat.DTO.MessageDTO;
import com.example.serviciochat.DTO.NuevoChatDTO;
import com.example.serviciochat.model.Chat;
import com.example.serviciochat.model.Message;

import java.util.List;
import java.util.Optional;

public interface IChatService {
    List<NuevoChatDTO> findAllChatsBUserId(String id);

    Optional<NuevoChatDTO> crearChat(String idUsuario1, String idUsuario2);

    List<MessageDTO> getAllMessages(String idUsuario1, String idUsuario2);
}
