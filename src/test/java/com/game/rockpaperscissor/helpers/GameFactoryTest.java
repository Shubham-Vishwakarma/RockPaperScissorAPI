package com.game.rockpaperscissor.helpers;

import com.game.rockpaperscissor.factory.GameFactory;
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
    void updateGameStatusToGameOverTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        GameFactory.updateGameStatus(game, Status.GAME_OVER);
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
    }

    @Test
    void updateGameStatusToReadyTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        GameFactory.updateGameStatus(game, Status.READY);
        Assertions.assertEquals(Status.READY, game.getStatus());
    }

    @Test
    void updateGameStatusToInProgressTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        GameFactory.updateGameStatus(game, Status.IN_PROGRESS);
        Assertions.assertEquals(Status.IN_PROGRESS, game.getStatus());
    }

    @Test
    void gameOverWhenServerWinsTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        game.setServerScore(3);
        GameFactory.updateIfGameOver(game);
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
        Assertions.assertEquals(SERVER, game.getWinner());
    }

    @Test
    void gameOverWhenUserWinsTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        game.setUserScore(3);
        GameFactory.updateIfGameOver(game);
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
        Assertions.assertEquals(USER, game.getWinner());
    }

    @Test
    void createGameStepTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        GameStep gameStep = GameFactory.createGameStep(game, Move.ROCK, Move.PAPER, USER);
        Assertions.assertEquals(game, gameStep.getGame());
        Assertions.assertEquals(Move.ROCK, gameStep.getUser());
        Assertions.assertEquals(Move.PAPER, gameStep.getServer());
        Assertions.assertEquals(USER, gameStep.getWinner());
    }

    @Test
    void updateUserGameScoreTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        int userScore = game.getUserScore();
        int serverScore = game.getServerScore();
        GameStep gameStep = GameFactory.createGameStep(game, Move.ROCK, Move.PAPER, USER);
        GameFactory.updateGameScore(game, gameStep);
        Assertions.assertEquals(userScore + 1, game.getUserScore());
        Assertions.assertEquals(serverScore, game.getServerScore());
    }

    @Test
    void updateServerGameScoreTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        int userScore = game.getUserScore();
        int serverScore = game.getServerScore();
        GameStep gameStep = GameFactory.createGameStep(game, Move.PAPER, Move.SCISSORS, SERVER);
        GameFactory.updateGameScore(game, gameStep);
        Assertions.assertEquals(userScore, game.getUserScore());
        Assertions.assertEquals(serverScore + 1, game.getServerScore());
    }
}
