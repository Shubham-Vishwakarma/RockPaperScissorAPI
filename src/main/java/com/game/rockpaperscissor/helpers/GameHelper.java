package com.game.rockpaperscissor.helpers;

import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.GameStep;
import com.game.rockpaperscissor.models.Move;
import com.game.rockpaperscissor.models.Status;

import java.util.Random;

import static com.game.rockpaperscissor.Constants.*;

public class GameHelper {
    private static final Random random = new Random();

    private GameHelper() {
    }

    public static String generateToken() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        StringBuilder builder = new StringBuilder(targetStringLength);

        for(int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + random.nextInt(rightLimit - leftLimit + 1);
            builder.append((char) randomLimitedInt);
        }

        return builder.toString();
    }

    public static String getWinner(Move userMove, Move serverMove) {

        if(userMove == Move.ROCK && serverMove == Move.SCISSOR)
            return USER;
        else if(serverMove == Move.ROCK && userMove == Move.SCISSOR)
            return SERVER;

        if(userMove == Move.PAPER && serverMove == Move.ROCK)
            return USER;
        else if(serverMove == Move.PAPER && userMove == Move.ROCK)
            return SERVER;

        if(userMove == Move.SCISSOR && serverMove == Move.PAPER)
            return USER;
        else if(serverMove == Move.SCISSOR && userMove == Move.PAPER)
            return SERVER;

        return TIE;
    }

    public static Move generateServerMove() {
        return Move.values()[random.nextInt(Move.values().length)];
    }

    public static Move generateServerAlwaysWinsMove(Move userMove) {
        return switch (userMove) {
            case ROCK -> Move.PAPER;
            case PAPER -> Move.SCISSOR;
            default -> Move.ROCK;
        };
    }

    public static Move generateServerAlwaysLoosesMove(Move userMove) {
        return switch (userMove) {
            case ROCK -> Move.SCISSOR;
            case PAPER -> Move.ROCK;
            default -> Move.PAPER;
        };
    }

    public static void updateGameScore(Game game, GameStep step) {
        String winner = step.getWinner();
        if(winner.equals(USER)) {
            game.setUserScore(game.getUserScore() + 1);
        }
        else if(winner.equals(SERVER)) {
            game.setServerScore(game.getServerScore() + 1);
        }
    }

    public static void updateIfGameOver(Game game) {
        if(game.getServerScore() == 3) {
            game.setWinner(SERVER);
            game.setStatus(Status.GAME_OVER);
        }
        else if(game.getUserScore() == 3) {
            game.setWinner(USER);
            game.setStatus(Status.GAME_OVER);
        }
    }
}
