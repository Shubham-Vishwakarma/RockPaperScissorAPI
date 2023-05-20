package com.game.rockpaperscissor.cucumber.steps;

import com.game.rockpaperscissor.cucumber.CommonSteps;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.Status;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static com.game.rockpaperscissor.Constants.*;

public class PlayGameSteps extends CommonSteps {

    String gameToken;

    @Before
    public void before() {
        this.gameToken = null;
    }

    @Given("a {string} level new game")
    public void a_new_game(String levelString) {
        ResponseEntity<Game> gameResponse = executeStartGame(levelString);
        Game game = gameResponse.getBody();
        Assertions.assertNotNull(game);
        this.gameToken = game.getToken();
    }

    @When("user plays {string}")
    public void user_plays(String move) {
        ResponseEntity<Object> gameResponse = executePlayGame(gameToken, move);
        Assertions.assertEquals(HttpStatus.OK, gameResponse.getStatusCode());
    }

    @Then("winner is unpredictable")
    public void winner_is_unpredictable() {
        ResponseEntity<Game> gameResponse = executeGetResults(this.gameToken);
        Game game = gameResponse.getBody();
        Assertions.assertNotNull(game);
        Assertions.assertEquals(3, game.getSteps().size());
        Assertions.assertTrue(() -> Arrays.stream(Status.values()).anyMatch(p -> p == game.getStatus()));

        String[] winners = new String[] { USER, SERVER, NOT_DECIDED };
        Assertions.assertTrue(() -> Arrays.stream(winners).anyMatch(p -> p.equals(game.getWinner())));
    }

    @Then("{string} wins the game")
    public void server_wins_the_game(String winner) {
        ResponseEntity<Game> gameResponse = executeGetResults(this.gameToken);
        Game game = gameResponse.getBody();
        Assertions.assertNotNull(game);
        Assertions.assertEquals(winner.toUpperCase(), game.getWinner());
        Assertions.assertEquals(Status.GAME_OVER, game.getStatus());
    }

    @When("a invalid game token is provided")
    public void a_invalid_game_token_is_provided() {
        this.gameToken = "dummyToken";
    }

    @Then("user is unable to play {string} with errorMessage {string}")
    public void user_is_unable_to_play(String move, String errorMessage) {
        ResponseEntity<Object> gameResponse = executePlayGame(gameToken, move);
        LinkedHashMap error = (LinkedHashMap) gameResponse.getBody();
        Assertions.assertNotNull(error);
        Assertions.assertEquals(errorMessage, error.get("message"));
    }

    @When("an ended game token is provided")
    public void an_ended_game_token_is_provided() {
        a_new_game("easy");
        user_plays("rock");
        user_plays("scissor");
        user_plays("paper");
    }
}
