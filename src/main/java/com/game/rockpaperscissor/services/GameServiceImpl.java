package com.game.rockpaperscissor.services;

import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.models.*;
import com.game.rockpaperscissor.repository.GameRepository;
import com.game.rockpaperscissor.repository.GameStepRepository;
import com.game.rockpaperscissor.factory.GameFactory;
import com.game.rockpaperscissor.helpers.GameHelper;
import com.game.rockpaperscissor.helpers.GameValidator;
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
    public Game startGame(GameLevel level) {
        Game newGame = GameFactory.createNewGame(level);
        return gameRepository.save(newGame);
    }

    @Override
    public Game playGame(String token, Move userMove) throws GameNotFoundException, GameOverException {
        Optional<Game> oGame = gameRepository.findById(token);

        // Check if Game exists and Game is not GAME_OVER
        GameValidator.validateGame(oGame);
        Game game = oGame.get();
        GameLevel gameLevel = game.getLevel();

        Move serverMove = switch (gameLevel) {
            case EASY -> // User Always Wins
                    GameHelper.generateServerAlwaysLoosesMove(userMove);
            case MEDIUM -> // Fair Game
                    GameHelper.generateServerMove();
            case HARD -> // Server Always Wins
                    GameHelper.generateServerAlwaysWinsMove(userMove);
        };

        String winner = GameHelper.getWinner(userMove, serverMove);

        GameStep gameStep = GameFactory.createGameStep(game, userMove, serverMove, winner);
        GameHelper.updateGameScore(game, gameStep);
        GameHelper.updateIfGameOver(game);

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
