package com.example.serviciochat.service;

import com.example.serviciochat.DTO.MessageDTO;
import com.example.serviciochat.DTO.NuevoChatDTO;
import com.example.serviciochat.model.Chat;
import com.example.serviciochat.model.Message;
import com.example.serviciochat.repository.ChatRepository;
import com.example.serviciochat.repository.UserRepository;
import com.example.serviciochat.utils.EncriptationHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService implements IChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository,
                       UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<NuevoChatDTO> findAllChatsBUserId(String id) {
        id = EncriptationHelper.desencriptarAES(id);

        List<Chat> all = chatRepository.findAll();
        List<NuevoChatDTO> chats = new ArrayList<>();
        for (Chat chat : all) {
            if (chat.getUser1().getId().toString().equals(id) || chat.getUser2().getId().toString().equals(id)) {
                chats.add(new NuevoChatDTO(chat.getId(), chat.getUser1().getId(), chat.getUser2().getId()));
            }
        }
        return chats;
    }

    @Override
    public Optional<NuevoChatDTO> crearChat(String idUsuario1, String idUsuario2) {
        idUsuario1 = EncriptationHelper.desencriptarAES(idUsuario1);
        idUsuario2 = EncriptationHelper.desencriptarAES(idUsuario2);

        try {

            Chat chat = new Chat();
            chat.setUser1(userRepository.findById(Long.parseLong(idUsuario1)).get());
            chat.setUser2(userRepository.findById(Long.parseLong(idUsuario2)).get());
            chat.setMessages(new ArrayList<>());
            chatRepository.save(chat);
            NuevoChatDTO nuevoChatDTO = new NuevoChatDTO(chat.getId(), chat.getUser1().getId(), chat.getUser2().getId());
            return Optional.of(nuevoChatDTO);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<MessageDTO> getAllMessages(String idUsuario1, String idUsuario2) {
        idUsuario1 = EncriptationHelper.desencriptarAES(idUsuario1);
        idUsuario2 = EncriptationHelper.desencriptarAES(idUsuario2);

        List<Chat> allChats = chatRepository.findAll();

        for (Chat chat : allChats) {
            boolean user1Matches = chat.getUser1().getId().toString().equals(idUsuario1);
            boolean user2Matches = chat.getUser2().getId().toString().equals(idUsuario2);

            boolean reversedUser1Matches = chat.getUser1().getId().toString().equals(idUsuario2);
            boolean reversedUser2Matches = chat.getUser2().getId().toString().equals(idUsuario1);

            if ((user1Matches && user2Matches) || (reversedUser1Matches && reversedUser2Matches)) {
                return chat.getMessages()
                        .stream()
                        .map(message -> new MessageDTO(
                                message.getId(),
                                message.getChat().getId(),
                                message.getOriginUser().getId(),
                                message.getDestinationUser().getId(),
                                message.getMessage(),
                                message.getDate(),
                                message.getHasBeenRead(),
                                message.getHasBeenReceived()
                        ))
                        .collect(Collectors.toList());
            }
        }

        // Return an empty list if no matching chat is found
        return List.of();
    }
}
