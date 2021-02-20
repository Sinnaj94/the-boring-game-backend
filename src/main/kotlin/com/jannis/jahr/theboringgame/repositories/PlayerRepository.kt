package com.jannis.jahr.theboringgame.repositories

import com.jannis.jahr.theboringgame.entities.Player
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository: CrudRepository<Player, Int> {
    fun findByUsername(username : String) : List<Player>
}