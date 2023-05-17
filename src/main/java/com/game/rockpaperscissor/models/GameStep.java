package com.game.rockpaperscissor.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "game_step")
public class GameStep {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "token_id", nullable = false)
    private Game game;

    @Enumerated(EnumType.STRING)
    @Column(name = "user", nullable = false)
    private Move user;

    @Enumerated(EnumType.STRING)
    @Column(name = "server", nullable = false)
    private Move server;

    @Column(name = "winner", nullable = false)
    private String winner;
}