package com.game.rockpaperscissor.factory;

import com.game.rockpaperscissor.helpers.GameHelper;
import com.game.rockpaperscissor.models.*;

import static com.game.rockpaperscissor.Constants.*;

public class GameFactory {

    private GameFactory() {
    }

    public static Game createNewGame(GameLevel level) {
        String token = GameHelper.generateToken();
        Game newGame = new Game();
        newGame.setToken(token);
        newGame.setStatus(Status.READY);
        newGame.setUserScore(0);
        newGame.setServerScore(0);
        newGame.setWinner(NOT_DECIDED);
        newGame.setLevel(level);
        return newGame;
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
