package com.game.rockpaperscissor.assembler;

import com.game.rockpaperscissor.controllers.GameController;
import com.game.rockpaperscissor.models.Game;
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
        return EntityModel.of(game,
                linkTo(methodOn(GameController.class).startGame()).withRel("start"),
                linkTo(methodOn(GameController.class).gameResults(game.getToken())).withRel("results"),
                linkTo(methodOn(GameController.class).playRandomGame(game.getToken(), "rock")).withRel("playGame"),
                linkTo(methodOn(GameController.class).playServerGame(game.getToken(), "rock")).withRel("serverAlwaysWins"));
    }
}
