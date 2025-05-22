package com.example.serviciochat.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MessageDTO(Long codigoMensaje, Long codigoChat, Long codigoUsuarioEmisor, Long codigoUsuarioDestinatario, String contenido, LocalDateTime fechaEnvio, Boolean leido, Boolean recibido) {
}
