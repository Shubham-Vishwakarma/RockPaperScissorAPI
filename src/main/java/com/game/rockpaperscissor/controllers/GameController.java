package com.game.rockpaperscissor.controllers;

import com.game.rockpaperscissor.assembler.GameModelAssembler;
import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.GameLevel;
import com.game.rockpaperscissor.models.Level;
import com.game.rockpaperscissor.models.Move;
import com.game.rockpaperscissor.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/startGame")
    public ResponseEntity<Object> startGame(@RequestBody Level level) {
        GameLevel l = GameLevel.valueOf(level.getLevel().toUpperCase(Locale.ROOT));
        Game newGame = service.startGame(l);
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
