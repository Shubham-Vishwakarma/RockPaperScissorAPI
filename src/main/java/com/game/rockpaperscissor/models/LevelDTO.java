package com.game.rockpaperscissor.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LevelDTO implements Serializable {
    private String level;
}
