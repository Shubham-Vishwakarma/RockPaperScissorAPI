package com.game.rockpaperscissor.controllers;

import com.game.rockpaperscissor.assembler.GameModelAssembler;
import com.game.rockpaperscissor.exceptions.GameNotFoundException;
import com.game.rockpaperscissor.exceptions.GameOverException;
import com.game.rockpaperscissor.models.*;
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
    public ResponseEntity<Object> startGame(@RequestBody LevelDTO levelDTO) {
        GameLevel l = GameLevel.valueOf(levelDTO.getLevel().toUpperCase(Locale.ROOT));
        Game newGame = service.startGame(l);
        EntityModel<Game> game = assembler.toModel(newGame);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @PostMapping("/playGame")
    public ResponseEntity<Object> playGame(@RequestBody PlayGameDTO playGameDTO)
            throws GameOverException, GameNotFoundException {
        String token = playGameDTO.getToken();
        Move move = Enum.valueOf(Move.class, playGameDTO.getMove().toUpperCase(Locale.ROOT));
        Game game = service.playGame(token, move);

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
