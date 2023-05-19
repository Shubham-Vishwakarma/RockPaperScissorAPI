package com.game.rockpaperscissor.cucumber.steps;

import com.game.rockpaperscissor.cucumber.CommonSteps;
import com.game.rockpaperscissor.models.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static com.game.rockpaperscissor.Constants.NOT_DECIDED;

public class StartGameSteps extends CommonSteps {

    ResponseEntity<Game> gameResponse;

    @When("the level is passed as {string}")
    public void the_level_is_passed_as(String levelString) {
        this.gameResponse = executeStartGame(levelString);
    }

    @Then("a new game of {string} level is created")
    public void a_new_game_of_level_is_created(String levelString) {
        Assertions.assertEquals(HttpStatus.CREATED, gameResponse.getStatusCode());

        GameLevel level = Enum.valueOf(GameLevel.class, levelString.toUpperCase(Locale.ROOT));
        Game newGame = this.gameResponse.getBody();

        Assertions.assertNotNull(newGame);
        Assertions.assertNotNull(newGame.getToken());
        Assertions.assertEquals(Status.READY, newGame.getStatus());
        Assertions.assertEquals(0, newGame.getUserScore());
        Assertions.assertEquals(0, newGame.getServerScore());
        Assertions.assertEquals(NOT_DECIDED, newGame.getWinner());
        Assertions.assertEquals(level, newGame.getLevel());
    }
}
