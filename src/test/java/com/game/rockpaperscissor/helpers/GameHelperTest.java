package com.game.rockpaperscissor.helpers;

import com.game.rockpaperscissor.models.Move;
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
        String winner = GameHelper.getWinner(Move.ROCK, Move.SCISSORS);
        assertEquals(USER, winner);
    }

    @Test
    void getWinnerServerRockTest() {
        String winner = GameHelper.getWinner(Move.SCISSORS, Move.ROCK);
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
        String winner = GameHelper.getWinner(Move.SCISSORS, Move.PAPER);
        assertEquals(USER, winner);
    }

    @Test
    void getWinnerServerScissorTest() {
        String winner = GameHelper.getWinner(Move.PAPER, Move.SCISSORS);
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
        String winner = GameHelper.getWinner(Move.SCISSORS, Move.SCISSORS);
        assertEquals(TIE, winner);
    }

    @Test
    void generateServerMovePaperWins() {
        Move move = GameHelper.generateServerMove(Move.ROCK);
        assertEquals(Move.PAPER, move);
    }

    @Test
    void generateServerMoveRockWins() {
        Move move = GameHelper.generateServerMove(Move.SCISSORS);
        assertEquals(Move.ROCK, move);
    }

    @Test
    void generateServerMoveScissorWins() {
        Move move = GameHelper.generateServerMove(Move.PAPER);
        assertEquals(Move.SCISSORS, move);
    }

    @Test
    void generateServerMoveRandomTest() {
        Move move = GameHelper.generateServerMove();
        assertTrue(Arrays.stream(Move.values()).anyMatch(m -> m == move));
    }
}
