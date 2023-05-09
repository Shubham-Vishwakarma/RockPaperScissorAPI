package com.vocera.rockpaperscissors.helpers;

import com.vocera.rockpaperscissors.exceptions.GameNotFoundException;
import com.vocera.rockpaperscissors.exceptions.GameOverException;
import com.vocera.rockpaperscissors.models.Game;
import com.vocera.rockpaperscissors.models.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class GameValidatorTest {

    @Test
    void validateGameNotFoundExceptionTest() {
        Assertions.assertThrows(GameNotFoundException.class,
                () -> GameValidator.validateGame(Optional.empty()));
    }

    @Test
    void validateGameOverExceptionTest() {
        Game game = new Game();
        game.setStatus(Status.GAME_OVER);
        Assertions.assertThrows(GameOverException.class,
                () -> GameValidator.validateGame(Optional.of(game)));
    }

    @Test
    void validGameTest() throws GameOverException, GameNotFoundException {
        Game game = new Game();
        game.setStatus(Status.IN_PROGRESS);
        boolean valid = GameValidator.validateGame(Optional.of(game));
        Assertions.assertTrue(valid);
    }

}
