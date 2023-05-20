package com.game.rockpaperscissor.assembler;

import com.game.rockpaperscissor.controllers.GameController;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.LevelDTO;
import com.game.rockpaperscissor.models.PlayGameDTO;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GameModelAssembler implements RepresentationModelAssembler<Game, EntityModel<Game>> {
    @SneakyThrows
    @Override
    public EntityModel<Game> toModel(Game game) {
        PlayGameDTO playGameDTO = new PlayGameDTO();
        playGameDTO.setToken(game.getToken());
        playGameDTO.setMove("rock");

        LevelDTO levelDTO = new LevelDTO();
        levelDTO.setLevel("medium");

        return EntityModel.of(game,
                linkTo(methodOn(GameController.class).startGame(levelDTO)).withRel("startGame"),
                linkTo(methodOn(GameController.class).playGame(playGameDTO)).withRel("playGame"),
                linkTo(methodOn(GameController.class).gameResults(game.getToken())).withRel("results"));
    }
}
