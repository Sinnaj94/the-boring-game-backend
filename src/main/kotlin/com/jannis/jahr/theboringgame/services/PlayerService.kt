package com.jannis.jahr.theboringgame.services

import com.jannis.jahr.theboringgame.entities.Player
import com.jannis.jahr.theboringgame.repositories.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface PlayerService {
    fun getPlayers() : MutableIterable<Player>?
    fun addPlayer(name : String) : Player?
    fun getPlayer(name : String) : Player?
}

@Service
class MyPlayerService : PlayerService {
    @Autowired
    lateinit var repository : PlayerRepository

    override fun getPlayers(): MutableIterable<Player> = repository.findAll()

    override fun addPlayer(name: String): Player? {
        if(getPlayer(name) != null) {
            return null
        }
        val player = Player(username = name)
        return repository.save(player)
    }

    override fun getPlayer(name: String): Player? {
        return repository.findByUsername(name).firstOrNull()
    }
}