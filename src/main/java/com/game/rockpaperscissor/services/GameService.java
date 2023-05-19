package com.game.rockpaperscissor.services;

import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.GameLevel;
import com.game.rockpaperscissor.models.Move;

public interface GameService {

    Game startGame(GameLevel level);

    Game playRandomGame(String token, Move userMove) throws GameNotFoundException, GameOverException;

    Game playServerGame(String token, Move userMove) throws GameNotFoundException, GameOverException;

    Game getGameResults(String token) throws GameNotFoundException;
}
