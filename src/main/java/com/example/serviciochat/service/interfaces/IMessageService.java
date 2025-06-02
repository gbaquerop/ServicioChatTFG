package com.example.serviciochat.service.interfaces;

import com.example.serviciochat.DTO.MessageDTO;
import org.springframework.stereotype.Service;

@Service
public interface IMessageService {
    MessageDTO createMessage(String idChat, String idEmisor, String idReceptor, String mensaje);

    Boolean modifyMessage(String idMensaje, String idUsuarioEmisor, String contenido, String recibido, String leido);
}
