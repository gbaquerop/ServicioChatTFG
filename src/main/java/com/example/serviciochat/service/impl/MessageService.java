package com.example.serviciochat.service.impl;

import com.example.serviciochat.DTO.MessageDTO;
import com.example.serviciochat.model.Message;
import com.example.serviciochat.repository.ChatRepository;
import com.example.serviciochat.repository.MessageRepository;
import com.example.serviciochat.repository.UserRepository;
import com.example.serviciochat.service.interfaces.IMessageService;
import com.example.serviciochat.utils.EncriptationHelper;
import org.springframework.stereotype.Service;

@Service
public class MessageService  implements IMessageService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final EncriptationHelper encriptationHelper;

    public MessageService(MessageRepository messageRepository,
                          ChatRepository chatRepository,
                           UserRepository userRepository,
                          EncriptationHelper encriptationHelper) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.encriptationHelper = encriptationHelper;
    }

    @Override
    public MessageDTO createMessage(String idChat, String idEmisor, String idReceptor, String mensaje) {
        idChat = encriptationHelper.desencriptarAES(idChat);
        idEmisor = encriptationHelper.desencriptarAES(idEmisor);
        idReceptor = encriptationHelper.desencriptarAES(idReceptor);

        Message message = new Message();
        message.setChat(chatRepository.findById(Long.parseLong(idChat)).get());
        message.setOriginUser(userRepository.findById(Long.parseLong(idEmisor)).get());
        message.setDestinationUser(userRepository.findById(Long.parseLong(idReceptor)).get());
        message.setMessage(mensaje);
        messageRepository.save(message);
        return new MessageDTO(message.getId(),
                message.getChat().getId(),
                message.getOriginUser().getId(),
                message.getDestinationUser().getId(),
                message.getMessage(),
                message.getDate(),
                message.getHasBeenRead(),
                message.getHasBeenReceived());

    }

    @Override
    public Boolean modifyMessage(String idMensaje, String idUsuarioEmisor, String contenido, String recibido, String leido) {
        idMensaje = encriptationHelper.desencriptarAES(idMensaje);
        idUsuarioEmisor = encriptationHelper.desencriptarAES(idUsuarioEmisor);
        recibido = encriptationHelper.desencriptarAES(recibido);
        leido = encriptationHelper.desencriptarAES(leido);

        Message message = messageRepository.findById(Long.parseLong(idMensaje)).get();
        if (message != null) {
            message.setMessage(contenido);
            message.setHasBeenReceived(Boolean.parseBoolean(recibido));
            message.setHasBeenRead(Boolean.parseBoolean(leido));
            messageRepository.save(message);
            return true;
        }
        return false;
    }
}
