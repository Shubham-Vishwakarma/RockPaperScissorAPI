package com.game.rockpaperscissor.controllers;

import com.game.rockpaperscissor.assembler.GameModelAssembler;
import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.models.*;
import com.game.rockpaperscissor.services.GameService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameControllerTest {

    GameController controller;
    GameService service;
    GameModelAssembler assembler;

    @BeforeAll
    void setUp() {
        service = Mockito.mock(GameService.class);
        assembler = new GameModelAssembler();
        controller = new GameController(service, assembler);
    }

    @Test
    void startGameTest() {
        Game game = new Game();
        game.setToken("dummyToken");
        when(service.startGame(GameLevel.EASY)).thenReturn(game);
        LevelDTO levelDTO = new LevelDTO();
        levelDTO.setLevel("easy");

        ResponseEntity<Object> responseGame = controller.startGame(levelDTO);
        EntityModel<Game> body = (EntityModel<Game>) responseGame.getBody();
        String responseToken = body.getContent().getToken();

        Assertions.assertEquals(HttpStatus.CREATED, responseGame.getStatusCode());
        Assertions.assertEquals("dummyToken", responseToken);
    }

    @Test
    void playRandomGameTest() throws GameOverException, GameNotFoundException {
        Game game = new Game();
        game.setToken("dummyToken");
        game.setStatus(Status.IN_PROGRESS);
        PlayGameDTO playGameDTO = new PlayGameDTO();
        playGameDTO.setToken("dummyToken");
        playGameDTO.setMove("paper");
        when(service.playGame("dummyToken", Move.PAPER)).thenReturn(game);

        ResponseEntity<Object> responseGame = controller.playGame(playGameDTO);
        EntityModel<Game> body = (EntityModel<Game>) responseGame.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseGame.getStatusCode());
        Assertions.assertEquals("dummyToken", body.getContent().getToken());
        Assertions.assertEquals(Status.IN_PROGRESS, body.getContent().getStatus());
    }

    @Test
    void gameResultsTest() throws GameNotFoundException {
        Game game = new Game();
        game.setToken("dummyToken");
        game.setStatus(Status.GAME_OVER);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken("dummyToken");

        when(service.getGameResults("dummyToken")).thenReturn(game);

        ResponseEntity<Object> responseGame = controller.gameResults(tokenDTO);
        EntityModel<Game> body = (EntityModel<Game>) responseGame.getBody();

        Assertions.assertEquals(HttpStatus.OK, responseGame.getStatusCode());
        Assertions.assertEquals("dummyToken", body.getContent().getToken());
        Assertions.assertEquals(Status.GAME_OVER, body.getContent().getStatus());
    }

    @AfterAll
    void tearDown() {
        controller = null;
        service = null;
        assembler = null;
    }
}
