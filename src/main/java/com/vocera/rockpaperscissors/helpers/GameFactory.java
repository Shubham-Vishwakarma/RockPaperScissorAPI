package com.vocera.rockpaperscissors.helpers;

import com.vocera.rockpaperscissors.models.Game;
import com.vocera.rockpaperscissors.models.GameStep;
import com.vocera.rockpaperscissors.models.Move;
import com.vocera.rockpaperscissors.models.Status;

public class GameFactory {

    public static Game createNewGame() {

        String token = GameHelper.generateToken();

        Game newGame = new Game();
        newGame.setToken(token);
        newGame.setStatus(Status.READY);
        newGame.setUserScore(0);
        newGame.setServerScore(0);
        newGame.setWinner("NOT_DECIDED");

        return newGame;
    }

    public static void updateGameStatus(Game game, Status status) {
        game.setStatus(status);
    }

    public static void updateGameScore(Game game, GameStep step) {
        String winner = step.getWinner();
        if(winner.equals("USER")) {
            game.setUserScore(game.getUserScore() + 1);
        }
        else if(winner.equals("SERVER")) {
            game.setServerScore(game.getServerScore() + 1);
        }
    }

    public static void updateIfGameOver(Game game) {
        if(game.getServerScore() == 3) {
            game.setWinner("SERVER");
            game.setStatus(Status.GAME_OVER);
        }
        else if(game.getUserScore() == 3) {
            game.setWinner("USER");
            game.setStatus(Status.GAME_OVER);
        }
    }

    public static GameStep createGameStep(Game game, Move userMove, Move serverMove, String winner) {
        GameStep gameStep = new GameStep();
        gameStep.setGame(game);
        gameStep.setUser(userMove);
        gameStep.setServer(serverMove);
        gameStep.setWinner(winner);
        return gameStep;
    }
}
