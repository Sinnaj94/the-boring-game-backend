package com.jannis.jahr.theboringgame.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table
data class Player(
        @Id @GeneratedValue
        val id : Int = -1,
        val username : String = "",
        val score: Int = (-1000..1000).random()
)