package com.example.serviciochat.controller;

import com.example.serviciochat.DTO.MessageDTO;
import com.example.serviciochat.model.Message;
import com.example.serviciochat.service.IMessageService;
import com.example.serviciochat.service.MessageService;
import com.example.serviciochat.utils.EncriptationHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/message")
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/GuardarMensaje/{idChat}/{idEmisor}/{idReceptor}/{mensaje}")
    public ResponseEntity<MessageDTO> GuardarMensaje(@PathVariable String idChat,
                                                     @PathVariable String idEmisor,
                                                     @PathVariable String idReceptor,
                                                     @PathVariable String mensaje) {
        MessageDTO savedMessage = messageService.createMessage(idChat, idEmisor, idReceptor, mensaje);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/ModificarMensaje/{idMensaje}/{idUsuarioEmisor}/{contenido}/{recibido}/{leido}")
    public ResponseEntity<Boolean> ModificarMensaje(@PathVariable String idMensaje,
                                                    @PathVariable String idUsuarioEmisor,
                                                    @PathVariable String contenido,
                                                    @PathVariable String recibido,
                                                    @PathVariable String leido){
        Boolean bool = messageService.modifyMessage(idMensaje, idUsuarioEmisor, contenido, recibido, leido);
        return ResponseEntity.ok(bool);
    }

}
