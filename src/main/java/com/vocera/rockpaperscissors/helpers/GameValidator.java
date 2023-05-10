package com.vocera.rockpaperscissors.helpers;

import com.vocera.rockpaperscissors.exceptions.GameNotFoundException;
import com.vocera.rockpaperscissors.exceptions.GameOverException;
import com.vocera.rockpaperscissors.models.Game;
import com.vocera.rockpaperscissors.models.Status;

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
