package com.game.rockpaperscissor.cucumber;

import com.game.rockpaperscissor.models.Game;
import com.game.rockpaperscissor.models.LevelDTO;
import com.game.rockpaperscissor.models.PlayGameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public class CommonSteps {
    @Autowired
    protected TestRestTemplate restTemplate;

    protected final ResponseEntity<Game> executeStartGame(String levelString) {
        LevelDTO levelDTO = new LevelDTO();
        levelDTO.setLevel(levelString);
        return this.restTemplate.postForEntity("/startGame", levelDTO, Game.class);
    }

    protected final ResponseEntity<Game> executePlayGame(String token, String move) {
        PlayGameDTO playGameDTO = new PlayGameDTO();
        playGameDTO.setToken(token);
        playGameDTO.setMove(move);

        return this.restTemplate.postForEntity("/playGame", playGameDTO, Game.class);
    }

    protected final ResponseEntity<Game> executeGetResults(String token) {
        String url = "/" + token + "/results";
        return this.restTemplate.getForEntity(url, Game.class);
    }
}
