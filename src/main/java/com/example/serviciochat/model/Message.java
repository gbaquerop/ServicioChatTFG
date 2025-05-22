package com.example.serviciochat.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private User originUser;

    @NonNull
    @ManyToOne
    private User destinationUser;

    @NonNull
    @ManyToOne
    private Chat chat;

    @NonNull
    private String message;

    @NonNull
    private Boolean hasBeenRead = false;

    @NonNull
    private Boolean hasBeenReceived = false;

    @NonNull
    private LocalDateTime date = LocalDateTime.now();

}
