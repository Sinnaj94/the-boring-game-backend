package com.jannis.jahr.theboringgame.controllers
import com.jannis.jahr.theboringgame.entities.Player
import com.jannis.jahr.theboringgame.repositories.PlayerRepository
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest
@AutoConfigureMockMvc //need this in Spring Boot test
class GameControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockBean
    lateinit var playerRepository: PlayerRepository

    companion object {
        val player1 = Player(
                id = 0,
                username = "Jonas",
                score = 100
        )
        val player2 = Player(
                id = 1,
                username = "Abraham",
                score = -100
        )
    }

    @Test
    fun `Retrieve all Players - OK`() {
        given(playerRepository.findAll()).willReturn(mutableListOf(player1, player2))
        mockMvc.perform(get("/players").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(0))
                .andExpect(jsonPath("$[0].username").value("Jonas"))
                .andExpect(jsonPath("$[0].score").value(100))
                .andExpect(jsonPath("$[1].id").value(1))
                .andExpect(jsonPath("$[1].username").value("Abraham"))
                .andExpect(jsonPath("$[1].score").value(-100))
    }

    @Test
    fun `Add Player with name that exists - Conflict`() {
        given(playerRepository.findByUsername("Jonas")).willReturn(mutableListOf(player1))
        mockMvc.perform(post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", "Jonas")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun `Retrieve Player - OK`() {
        given(playerRepository.findByUsername("Jonas")).willReturn(mutableListOf(player1))
        mockMvc.perform(get("/players/Jonas")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.username").value("Jonas"))
                .andExpect(jsonPath("$.score").value(-100))
    }

    @Test
    fun `Retrieve Player - Not found`() {
        given(playerRepository.findByUsername("Jonas")).willReturn(mutableListOf(player1))
        mockMvc.perform(get("/players/Jakob")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
    }
}