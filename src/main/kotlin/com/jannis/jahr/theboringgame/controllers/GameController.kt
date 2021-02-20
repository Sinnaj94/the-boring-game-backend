package com.jannis.jahr.theboringgame.controllers

import com.jannis.jahr.theboringgame.entities.Player
import com.jannis.jahr.theboringgame.services.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Rest Controller for the most boring Game
 */
@RestController
class GameController {
    @Autowired
    lateinit var playerService: PlayerService

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Player does not exist")  // 404
    class NoSuchPlayerException: RuntimeException("Player does not exist.")

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not a valid name")  // 404
    class UsernameInvalidException: RuntimeException("The given username is invalid.")

    @ResponseStatus(value= HttpStatus.CONFLICT, reason="Player with the given name exists.")  // 403
    class PlayerExistsException: RuntimeException("Player with the given name exists.")


    @RequestMapping("/")
    fun home() : List<Player> {
        return playerService.getPlayers()?.toList() ?: emptyList()
    }

    @GetMapping("/players")
    fun getPlayers(): List<Player> {
        return playerService.getPlayers()?.toList() ?: emptyList()
    }

    @GetMapping("/players/{name}")
    fun getPlayerByName(@PathVariable name :String): Player {
        return playerService.getPlayer(name) ?: throw NoSuchPlayerException()
    }

    @PostMapping("/players")
    fun addPlayer(username : String) : Player {
        val trimmedUsername = username.trim()
        if(trimmedUsername.isEmpty()) {
            throw UsernameInvalidException()
        }
        return playerService.addPlayer(name = trimmedUsername) ?: throw PlayerExistsException()
    }
}