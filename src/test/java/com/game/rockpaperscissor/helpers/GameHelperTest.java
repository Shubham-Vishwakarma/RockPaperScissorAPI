package com.game.rockpaperscissor.helpers;

import com.game.rockpaperscissor.factory.GameFactory;
import com.game.rockpaperscissor.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.game.rockpaperscissor.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class GameHelperTest {

    @Test
    void generateTokenOfLengthTenTest() {
        String token = GameHelper.generateToken();
        assertEquals(10, token.length());
    }

    @Test
    void generateRandomTokenAlwaysTest() {
        String token1 = GameHelper.generateToken();
        String token2 = GameHelper.generateToken();
        assertNotEquals(token1, token2);
    }

    @Test
    void getWinnerUserRockWinsTest() {
        String winner = GameHelper.getWinner(Move.ROCK, Move.SCISSOR);
        assertEquals(USER, winner);
    }

    @Test
    void getWinnerServerRockTest() {
        String winner = GameHelper.getWinner(Move.SCISSOR, Move.ROCK);
        assertEquals(SERVER, winner);
    }

    @Test
    void getWinnerUserPaperWinsTest() {
        String winner = GameHelper.getWinner(Move.PAPER, Move.ROCK);
        assertEquals(USER, winner);
    }

    @Test
    void getWinnerServerPaperTest() {
        String winner = GameHelper.getWinner(Move.ROCK, Move.PAPER);
        assertEquals(SERVER, winner);
    }

    @Test
    void getWinnerUserScissorWinsTest() {
        String winner = GameHelper.getWinner(Move.SCISSOR, Move.PAPER);
        assertEquals(USER, winner);
    }

    @Test
    void getWinnerServerScissorTest() {
        String winner = GameHelper.getWinner(Move.PAPER, Move.SCISSOR);
        assertEquals(SERVER, winner);
    }

    @Test
    void getWinnerServerPaperTieTest() {
        String winner = GameHelper.getWinner(Move.PAPER, Move.PAPER);
        assertEquals(TIE, winner);
    }

    @Test
    void getWinnerServerRockTieTest() {
        String winner = GameHelper.getWinner(Move.ROCK, Move.ROCK);
        assertEquals(TIE, winner);
    }

    @Test
    void getWinnerServerScissorTieTest() {
        String winner = GameHelper.getWinner(Move.SCISSOR, Move.SCISSOR);
        assertEquals(TIE, winner);
    }

    @Test
    void generateServerAlwaysWinsMovePaperWins() {
        Move move = GameHelper.generateServerAlwaysWinsMove(Move.ROCK);
        assertEquals(Move.PAPER, move);
    }

    @Test
    void generateServerAlwaysWinsMoveRockWins() {
        Move move = GameHelper.generateServerAlwaysWinsMove(Move.SCISSOR);
        assertEquals(Move.ROCK, move);
    }

    @Test
    void generateServerAlwaysWinsMoveScissorWins() {
        Move move = GameHelper.generateServerAlwaysWinsMove(Move.PAPER);
        assertEquals(Move.SCISSOR, move);
    }

    @Test
    void generateServerMoveRandomTest() {
        Move move = GameHelper.generateServerMove();
        assertTrue(Arrays.stream(Move.values()).anyMatch(m -> m == move));
    }

    @Test
    void generateServerAlwaysLoosesMoveRockWins() {
        Move move = GameHelper.generateServerAlwaysLoosesMove(Move.ROCK);
        assertEquals(Move.SCISSOR, move);
    }

    @Test
    void generateServerAlwaysLoosesMoveScissorWins() {
        Move move = GameHelper.generateServerAlwaysLoosesMove(Move.SCISSOR);
        assertEquals(Move.PAPER, move);
    }

    @Test
    void generateServerAlwaysLoosesMovePaperWins() {
        Move move = GameHelper.generateServerAlwaysLoosesMove(Move.PAPER);
        assertEquals(Move.ROCK, move);
    }

    @Test
    void gameOverWhenServerWinsTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        game.setServerScore(3);
        GameHelper.updateIfGameOver(game);
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
        Assertions.assertEquals(SERVER, game.getWinner());
    }

    @Test
    void gameOverWhenUserWinsTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        game.setUserScore(3);
        GameHelper.updateIfGameOver(game);
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
        Assertions.assertEquals(USER, game.getWinner());
    }

    @Test
    void updateUserGameScoreTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        int userScore = game.getUserScore();
        int serverScore = game.getServerScore();
        GameStep gameStep = GameFactory.createGameStep(game, Move.ROCK, Move.PAPER, USER);
        GameHelper.updateGameScore(game, gameStep);
        Assertions.assertEquals(userScore + 1, game.getUserScore());
        Assertions.assertEquals(serverScore, game.getServerScore());
    }

    @Test
    void updateServerGameScoreTest() {
        Game game = GameFactory.createNewGame(GameLevel.EASY);
        int userScore = game.getUserScore();
        int serverScore = game.getServerScore();
        GameStep gameStep = GameFactory.createGameStep(game, Move.PAPER, Move.SCISSOR, SERVER);
        GameHelper.updateGameScore(game, gameStep);
        Assertions.assertEquals(userScore, game.getUserScore());
        Assertions.assertEquals(serverScore + 1, game.getServerScore());
    }
}
