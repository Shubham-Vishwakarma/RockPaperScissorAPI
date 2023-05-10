package com.vocera.rockpaperscissors.services;

import com.vocera.rockpaperscissors.exceptions.GameNotFoundException;
import com.vocera.rockpaperscissors.exceptions.GameOverException;
import com.vocera.rockpaperscissors.models.Game;
import com.vocera.rockpaperscissors.models.Move;

public interface GameService {

    Game startGame();

    Game playRandomGame(String token, Move userMove) throws GameNotFoundException, GameOverException;

    Game playServerGame(String token, Move userMove) throws GameNotFoundException, GameOverException;

    Game getGameResults(String token) throws GameNotFoundException;
}
