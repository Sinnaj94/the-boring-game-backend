package com.jannis.jahr.theboringgame

import com.jannis.jahr.theboringgame.controllers.GameController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc




@SpringBootTest
class TheBoringGameApplicationTests {

    @Autowired
    lateinit var gameController : GameController

    @Test
    fun contextLoads() {
        assertThat(gameController).isNotNull
    }

}
