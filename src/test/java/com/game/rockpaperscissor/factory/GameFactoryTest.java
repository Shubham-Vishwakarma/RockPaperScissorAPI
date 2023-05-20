package com.game.rockpaperscissor.factory;

import com.game.rockpaperscissor.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.game.rockpaperscissor.Constants.*;


class GameFactoryTest {

    @Test
    void newGamesShouldHaveUniqueTokenTest() {
        Game game1 = GameFactory.createNewGame(GameLevel.EASY);
        Game game2 = GameFactory.createNewGame(GameLevel.EASY);
        Assertions.assertEquals(10, game1.getToken().length());
        Assertions.assertEquals(10, game2.getToken().length());
        Assertions.assertNotEquals(game1.getToken() , game2.getToken());
    }

    @Test
    void newGameShouldBeInReadyStateTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        Assertions.assertEquals(Status.READY, game.getStatus());
    }

    @Test
    void newGameShouldHaveZeroScoresTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        Assertions.assertEquals(0, game.getUserScore());
        Assertions.assertEquals(0, game.getServerScore());
    }

    @Test
    void newGameShouldHaveNoWinnerTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        Assertions.assertEquals(NOT_DECIDED, game.getWinner());
    }

    @Test
    void newGameEasyLevelCreatedTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        Assertions.assertEquals(GameLevel.EASY, game.getLevel());
    }

    @Test
    void newGameMediumLevelCreatedTest() {
        Game game = GameFactory.createNewGame(GameLevel.MEDIUM);
        Assertions.assertEquals(GameLevel.MEDIUM, game.getLevel());
    }

    @Test
    void newGameHardLevelCreatedTest() {
        Game game = GameFactory.createNewGame(GameLevel.HARD);
        Assertions.assertEquals(GameLevel.HARD, game.getLevel());
    }

    @Test
    void createGameStepTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        GameStep gameStep = GameFactory.createGameStep(game, Move.ROCK, Move.PAPER, SERVER);
        Assertions.assertEquals(game, gameStep.getGame());
        Assertions.assertEquals(Move.ROCK, gameStep.getUser());
        Assertions.assertEquals(Move.PAPER, gameStep.getServer());
        Assertions.assertEquals(SERVER, gameStep.getWinner());
    }
}
