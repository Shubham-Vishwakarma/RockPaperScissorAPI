package com.game.rockpaperscissor.helpers;

import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.Status;

import java.util.Optional;

public class GameValidator {

    private GameValidator() {}

    @SuppressWarnings("ALL")
    public static boolean validateGame(Optional<Game> game) throws GameNotFoundException, GameOverException {

        if(game.isEmpty()) {
            throw new GameNotFoundException("Cannot find game with given token");
        }

        if(game.get().getStatus() == Status.GAME_OVER) {
            throw new GameOverException("Game Over. Please start a new game");
        }

        return true;
    }

}
