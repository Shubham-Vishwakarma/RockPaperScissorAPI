package com.vocera.rockpaperscissors;

import com.vocera.rockpaperscissors.assembler.GameModelAssembler;
import com.vocera.rockpaperscissors.controllers.GameController;
import com.vocera.rockpaperscissors.helpers.GameFactory;
import com.vocera.rockpaperscissors.models.Game;
import com.vocera.rockpaperscissors.services.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GameController.class)
public class RockPaperScissorsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameModelAssembler assembler;

	@MockBean
	private GameService gameService;

	@Test
	void startGameTest() throws Exception {
		Game game = GameFactory.createNewGame();
		Mockito.when(gameService.startGame()).thenReturn(game);

		MockHttpServletRequestBuilder builders =
				MockMvcRequestBuilders.get("/start")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builders)
				.andExpect(status().isCreated());
	}

}
