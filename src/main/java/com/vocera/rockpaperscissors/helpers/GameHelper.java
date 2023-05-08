package com.vocera.rockpaperscissors.helpers;

import com.vocera.rockpaperscissors.models.Move;

import java.util.Random;

import static com.vocera.rockpaperscissors.Constants.*;

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

        if(userMove == Move.ROCK && serverMove == Move.SCISSORS)
            return USER;
        else if(serverMove == Move.ROCK && userMove == Move.SCISSORS)
            return SERVER;

        if(userMove == Move.PAPER && serverMove == Move.ROCK)
            return USER;
        else if(serverMove == Move.PAPER && userMove == Move.ROCK)
            return SERVER;

        if(userMove == Move.SCISSORS && serverMove == Move.PAPER)
            return USER;
        else if(serverMove == Move.SCISSORS && userMove == Move.PAPER)
            return SERVER;

        return TIE;
    }

    public static Move generateServerMove() {
        return Move.values()[random.nextInt(Move.values().length)];
    }

    public static Move generateServerMove(Move userMove) {
        switch (userMove) {
            case ROCK: return Move.PAPER;
            case PAPER: return Move.SCISSORS;
            case SCISSORS:
            default:
                return Move.ROCK;
        }
    }

}
