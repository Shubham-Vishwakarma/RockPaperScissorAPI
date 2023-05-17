package com.game.rockpaperscissor.helpers;

import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.GameStep;
import com.game.rockpaperscissor.models.Move;
import com.game.rockpaperscissor.models.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.game.rockpaperscissor.Constants.*;


class GameFactoryTest {

    @Test
    void newGamesShouldHaveUniqueTokenTest() {
        Game game1 = GameFactory.createNewGame();
        Game game2 = GameFactory.createNewGame();
        Assertions.assertEquals(10, game1.getToken().length());
        Assertions.assertEquals(10, game2.getToken().length());
        Assertions.assertNotEquals(game1.getToken() , game2.getToken());
    }

    @Test
    void newGameShouldBeInReadyStateTest() {
        Game game = GameFactory.createNewGame();
        Assertions.assertEquals(Status.READY, game.getStatus());
    }

    @Test
    void newGameShouldHaveZeroScoresTest() {
        Game game = GameFactory.createNewGame();
        Assertions.assertEquals(0, game.getUserScore());
        Assertions.assertEquals(0, game.getServerScore());
    }

    @Test
    void newGameShouldHaveNoWinnerTest() {
        Game game = GameFactory.createNewGame();
        Assertions.assertEquals(NOT_DECIDED, game.getWinner());
    }

    @Test
    void updateGameStatusToGameOverTest() {
        Game game = GameFactory.createNewGame();
        GameFactory.updateGameStatus(game, Status.GAME_OVER);
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
    }

    @Test
    void updateGameStatusToReadyTest() {
        Game game = GameFactory.createNewGame();
        GameFactory.updateGameStatus(game, Status.READY);
        Assertions.assertEquals(Status.READY, game.getStatus());
    }

    @Test
    void updateGameStatusToInProgressTest() {
        Game game = GameFactory.createNewGame();
        GameFactory.updateGameStatus(game, Status.IN_PROGRESS);
        Assertions.assertEquals(Status.IN_PROGRESS, game.getStatus());
    }

    @Test
    void gameOverWhenServerWinsTest() {
        Game game = GameFactory.createNewGame();
        game.setServerScore(3);
        GameFactory.updateIfGameOver(game);
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
        Assertions.assertEquals(SERVER, game.getWinner());
    }

    @Test
    void gameOverWhenUserWinsTest() {
        Game game = GameFactory.createNewGame();
        game.setUserScore(3);
        GameFactory.updateIfGameOver(game);
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
        Assertions.assertEquals(USER, game.getWinner());
    }

    @Test
    void createGameStepTest() {
        Game game = GameFactory.createNewGame();
        GameStep gameStep = GameFactory.createGameStep(game, Move.ROCK, Move.PAPER, USER);
        Assertions.assertEquals(game, gameStep.getGame());
        Assertions.assertEquals(Move.ROCK, gameStep.getUser());
        Assertions.assertEquals(Move.PAPER, gameStep.getServer());
        Assertions.assertEquals(USER, gameStep.getWinner());
    }

    @Test
    void updateUserGameScoreTest() {
        Game game = GameFactory.createNewGame();
        int userScore = game.getUserScore();
        int serverScore = game.getServerScore();
        GameStep gameStep = GameFactory.createGameStep(game, Move.ROCK, Move.PAPER, USER);
        GameFactory.updateGameScore(game, gameStep);
        Assertions.assertEquals(userScore + 1, game.getUserScore());
        Assertions.assertEquals(serverScore, game.getServerScore());
    }

    @Test
    void updateServerGameScoreTest() {
        Game game = GameFactory.createNewGame();
        int userScore = game.getUserScore();
        int serverScore = game.getServerScore();
        GameStep gameStep = GameFactory.createGameStep(game, Move.PAPER, Move.SCISSORS, SERVER);
        GameFactory.updateGameScore(game, gameStep);
        Assertions.assertEquals(userScore, game.getUserScore());
        Assertions.assertEquals(serverScore + 1, game.getServerScore());
    }
}
