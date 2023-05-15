package com.vocera.rockpaperscissors.exceptions;

import com.vocera.rockpaperscissors.controllers.GameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameController controller;

    @Test
    void handleUnknownExceptionTest() throws Exception {
        when(controller.startGame()).thenThrow(new RuntimeException("Test Exception"));

        this.mockMvc.perform(get("/game/start"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("INTERNAL_SERVER_ERROR")))
                .andExpect(jsonPath("$.message", is(("Something Went Wrong!!!"))))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void handleNoHandlerFoundExceptionTest() throws Exception {
        this.mockMvc.perform(get("/invalidUrl"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is(("No endpoint GET /invalidUrl."))))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void handleGameNotFoundExceptionTest() throws Exception {
        when(controller.gameResults("dummyToken")).thenThrow(new GameNotFoundException("Cannot find game with given token"));

        this.mockMvc.perform(get("/dummyToken/results"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is(("No Game Found"))))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void handleGameOverExceptionTest() throws Exception {
        when(controller.playRandomGame("dummyToken", "rock")).thenThrow(new GameOverException("Game Over"));

        this.mockMvc.perform(get("/v1/dummyToken/rock"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.message", is(("Game Over"))))
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }
}