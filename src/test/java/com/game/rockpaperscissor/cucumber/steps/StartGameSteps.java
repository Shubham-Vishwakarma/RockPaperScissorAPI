package com.game.rockpaperscissor.cucumber.steps;

import com.game.rockpaperscissor.cucumber.CommonSteps;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.Status;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

import static com.game.rockpaperscissor.Constants.NOT_DECIDED;

public class StartGameSteps extends CommonSteps {

    ResponseEntity<Game> gameResponse;

    @When("the request is sent to url {string}")
    public void the_request_is_sent_to_url(String url) {
        this.gameResponse = this.restTemplate.getForEntity(url, Game.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        Assertions.assertEquals(this.gameResponse.getStatusCode().value(), statusCode);
    }

    @And("a new game is created")
    public void a_new_game_is_created() {
        Game newGame = this.gameResponse.getBody();

        Assertions.assertNotNull(newGame);
        Assertions.assertNotNull(newGame.getToken());
        Assertions.assertEquals(Status.READY, newGame.getStatus());
        Assertions.assertEquals(0, newGame.getUserScore());
        Assertions.assertEquals(0, newGame.getServerScore());
        Assertions.assertEquals(NOT_DECIDED, newGame.getWinner());
    }
}
