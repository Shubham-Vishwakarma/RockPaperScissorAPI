package com.vocera.rockpaperscissors.services;

import com.vocera.rockpaperscissors.exceptions.GameNotFoundException;
import com.vocera.rockpaperscissors.exceptions.GameOverException;
import com.vocera.rockpaperscissors.helpers.GameFactory;
import com.vocera.rockpaperscissors.helpers.GameHelper;
import com.vocera.rockpaperscissors.helpers.GameValidator;
import com.vocera.rockpaperscissors.models.Game;
import com.vocera.rockpaperscissors.models.GameStep;
import com.vocera.rockpaperscissors.models.Move;
import com.vocera.rockpaperscissors.models.Status;
import com.vocera.rockpaperscissors.repository.GameRepository;
import com.vocera.rockpaperscissors.repository.GameStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameStepRepository gameStepRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameStepRepository gameStepRepository) {
        this.gameRepository = gameRepository;
        this.gameStepRepository = gameStepRepository;
    }

    @Override
    public Game startGame() {
        Game newGame = GameFactory.createNewGame();
        return gameRepository.save(newGame);
    }

    @Override
    public Game playRandomGame(String token, Move userMove) throws GameNotFoundException, GameOverException {
        Optional<Game> oGame = gameRepository.findById(token);

        // Check if Game exists and Game is not GAME_OVER
        GameValidator.validateGame(oGame);

        Game game = oGame.get();
        GameFactory.updateGameStatus(game, Status.IN_PROGRESS);

        Move serverMove = GameHelper.generateServerMove();
        String winner = GameHelper.getWinner(userMove, serverMove);

        GameStep gameStep = GameFactory.createGameStep(game, userMove, serverMove, winner);
        GameFactory.updateGameScore(game, gameStep);
        GameFactory.updateIfGameOver(game);

        gameRepository.save(game);
        gameStepRepository.save(gameStep);

        game.getSteps().add(gameStep);

        return game;
    }

    @Override
    public Game playServerGame(String token, Move userMove) throws GameNotFoundException, GameOverException {
        Optional<Game> oGame = gameRepository.findById(token);

        // Check if Game exists and Game is not GAME_OVER
        GameValidator.validateGame(oGame);

        Game game = oGame.get();
        game.setStatus(Status.IN_PROGRESS);

        Move serverMove = GameHelper.generateServerMove(userMove);
        String winner = GameHelper.getWinner(userMove, serverMove);

        GameStep gameStep = GameFactory.createGameStep(game, userMove, serverMove, winner);
        GameFactory.updateGameScore(game, gameStep);
        GameFactory.updateIfGameOver(game);

        gameRepository.save(game);
        gameStepRepository.save(gameStep);

        game.getSteps().add(gameStep);

        return game;
    }

    @Override
    public Game getGameResults(String token) throws GameNotFoundException {

        Optional<Game> oGame = gameRepository.findById(token);

        // Check if Game exists and Game is not GAME_OVER
        if(oGame.isEmpty())
            throw new GameNotFoundException("Cannot find game with given token");

        return oGame.get();
    }
}
