package com.game.rockpaperscissor.services;

import com.game.rockpaperscissor.Constants;
import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.helpers.GameFactory;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.GameStep;
import com.game.rockpaperscissor.models.Move;
import com.game.rockpaperscissor.repository.GameRepository;
import com.game.rockpaperscissor.repository.GameStepRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

class GameServiceImplTest {

    GameService gameService;
    GameRepository gameRepository;
    GameStepRepository gameStepRepository;

    @BeforeEach
    void onSetup() {
        gameRepository = Mockito.mock(GameRepository.class);
        gameStepRepository = Mockito.mock(GameStepRepository.class);
        gameService = new GameServiceImpl(gameRepository, gameStepRepository);
    }

    @Test
    void startGameTest() {
        gameService.startGame();
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void playRandomGameTest() throws GameOverException, GameNotFoundException {
        String token = "dummyToken";
        Game game = GameFactory.createNewGame();
        int steps = game.getSteps().size();
        when(gameRepository.findById(token)).thenReturn(Optional.of(game));

        Game resultGame = gameService.playRandomGame(token, Move.ROCK);

        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameStepRepository, times(1)).save(any(GameStep.class));
        Assertions.assertEquals(steps + 1, resultGame.getSteps().size());
    }

    @Test
    void playServerGameTest() throws GameOverException, GameNotFoundException {
        String token = "dummyToken";
        Game game = GameFactory.createNewGame();
        int steps = game.getSteps().size();
        when(gameRepository.findById(token)).thenReturn(Optional.of(game));

        Game resultGame = gameService.playServerGame(token, Move.ROCK);
        GameStep[] gameSteps = resultGame.getSteps().toArray(new GameStep[0]);

        Assertions.assertEquals(Constants.SERVER, gameSteps[gameSteps.length - 1].getWinner());
        verify(gameRepository, times(1)).save(any(Game.class));
        verify(gameStepRepository, times(1)).save(any(GameStep.class));
        Assertions.assertEquals(steps + 1, resultGame.getSteps().size());
    }

    @Test
    void getGameResultsTest() throws GameNotFoundException {
        String token = "dummyToken";
        Game game = GameFactory.createNewGame();
        when(gameRepository.findById(token)).thenReturn(Optional.of(game));

        Game resultGame = gameService.getGameResults(token);

        Assertions.assertNotNull(resultGame);
    }

    @Test
    void getGameResultsGameNotFoundTest() {
        String token = "dummyToken";
        when(gameRepository.findById(token)).thenReturn(Optional.empty());

        Assertions.assertThrows(GameNotFoundException.class, () -> gameService.getGameResults(token));
    }

    @AfterEach
    void onTearDown() {
        gameService = null;
        gameRepository = null;
        gameStepRepository = null;
    }
}
