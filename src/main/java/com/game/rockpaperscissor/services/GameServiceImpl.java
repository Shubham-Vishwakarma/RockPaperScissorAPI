package com.game.rockpaperscissor.services;

import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.repository.GameRepository;
import com.game.rockpaperscissor.repository.GameStepRepository;
import com.game.rockpaperscissor.helpers.GameFactory;
import com.game.rockpaperscissor.helpers.GameHelper;
import com.game.rockpaperscissor.helpers.GameValidator;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.GameStep;
import com.game.rockpaperscissor.models.Move;
import com.game.rockpaperscissor.models.Status;
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
