package com.vocera.rockpaperscissors.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game {

    @Id
    @Column(name = "token", nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "userScore", nullable = false)
    private int userScore = 0;

    @Column(name = "serverScore", nullable = false)
    private int serverScore = 0;

    @Column(name = "winner", nullable = false)
    private String winner = "NOT_DECIDED";

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<GameStep> steps = new LinkedHashSet<>();
}