package com.game.rockpaperscissor.cucumber;

import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.Level;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public class CommonSteps {
    @Autowired
    protected TestRestTemplate restTemplate;

    protected final ResponseEntity<Game> executeStartGame(String levelString) {
        Level level = new Level();
        level.setLevel(levelString);
        return this.restTemplate.postForEntity("/startGame", level, Game.class);
    }
}
