package com.vocera.rockpaperscissors.controllers;

import com.vocera.rockpaperscissors.assembler.GameModelAssembler;
import com.vocera.rockpaperscissors.exceptions.GameNotFoundException;
import com.vocera.rockpaperscissors.exceptions.GameOverException;
import com.vocera.rockpaperscissors.models.Game;
import com.vocera.rockpaperscissors.models.Move;
import com.vocera.rockpaperscissors.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class GameController {

    private final GameService service;
    private final GameModelAssembler assembler;

    @Autowired
    public GameController(GameService service, GameModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/game/start")
    public ResponseEntity<Object> startGame() {
        Game newGame = service.startGame();
        EntityModel<Game> game = assembler.toModel(newGame);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @GetMapping("/v1/{token}/{move}")
    public ResponseEntity<Object> playRandomGame(@PathVariable("token") String token, @PathVariable("move") String move)
            throws GameOverException, GameNotFoundException {
        Move m = Enum.valueOf(Move.class, move.toUpperCase(Locale.ROOT));
        Game game = service.playRandomGame(token, m);

        EntityModel<Game> entityGame = assembler.toModel(game);
        return new ResponseEntity<>(entityGame, HttpStatus.OK);
    }

    @GetMapping("/v2/{token}/{move}")
    public ResponseEntity<Object> playServerGame(@PathVariable("token") String token, @PathVariable("move") String move)
            throws GameOverException, GameNotFoundException {
        Move m = Enum.valueOf(Move.class, move.toUpperCase(Locale.ROOT));
        Game game = service.playServerGame(token, m);

        EntityModel<Game> entityGame = assembler.toModel(game);
        return new ResponseEntity<>(entityGame, HttpStatus.OK);
    }

    @GetMapping("/{token}/results")
    public ResponseEntity<Object> gameResults(@PathVariable("token") String token) throws GameNotFoundException {
        Game game = service.getGameResults(token);
        EntityModel<Game> entityGame = assembler.toModel(game);
        return new ResponseEntity<>(entityGame, HttpStatus.OK);
    }
}
