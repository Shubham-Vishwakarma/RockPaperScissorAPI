package com.game.rockpaperscissor.controllers;

import com.game.rockpaperscissor.assembler.GameModelAssembler;
import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.Move;
import com.game.rockpaperscissor.models.Status;
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
        when(service.startGame()).thenReturn(game);

        ResponseEntity<Object> responseGame = controller.startGame();
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

        when(service.playRandomGame("dummyToken", Move.PAPER)).thenReturn(game);

        ResponseEntity<Object> responseGame = controller.playRandomGame("dummyToken", "paper");
        EntityModel<Game> body = (EntityModel<Game>) responseGame.getBody();
        Assertions.assertEquals(HttpStatus.OK, responseGame.getStatusCode());
        Assertions.assertEquals("dummyToken", body.getContent().getToken());
        Assertions.assertEquals(Status.IN_PROGRESS, body.getContent().getStatus());
    }

    @Test
    void playServerGameTest() throws GameOverException, GameNotFoundException {
        Game game = new Game();
        game.setToken("dummyToken");
        game.setStatus(Status.IN_PROGRESS);

        when(service.playServerGame("dummyToken", Move.PAPER)).thenReturn(game);

        ResponseEntity<Object> responseGame = controller.playServerGame("dummyToken", "paper");
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

        when(service.getGameResults("dummyToken")).thenReturn(game);

        ResponseEntity<Object> responseGame = controller.gameResults("dummyToken");
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
