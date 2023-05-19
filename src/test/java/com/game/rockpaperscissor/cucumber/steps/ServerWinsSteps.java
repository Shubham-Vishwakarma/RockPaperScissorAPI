package com.game.rockpaperscissor.cucumber.steps;

import com.game.rockpaperscissor.cucumber.CommonSteps;
import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.Level;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.game.rockpaperscissor.Constants.SERVER;

public class ServerWinsSteps extends CommonSteps {

    String gameToken;

    @Given("a new game")
    public void a_new_game() {
        ResponseEntity<Game> gameResponse = executeStartGame("easy");
        this.gameToken = gameResponse.getBody().getToken();
    }

    @When("user plays {string}")
    public void user_plays(String move) {
        String url = "/v2/" + this.gameToken + "/" + move;
        ResponseEntity<Game> gameResponse = this.restTemplate.getForEntity(url, Game.class);
        Assertions.assertEquals(HttpStatus.OK, gameResponse.getStatusCode());
    }

    @Then("server wins the game")
    public void server_wins_the_game() {
        String url = "/" + this.gameToken + "/results";
        ResponseEntity<Game> gameResponse = this.restTemplate.getForEntity(url, Game.class);
        Assertions.assertEquals(SERVER, gameResponse.getBody().getWinner());
    }

}
