package com.jannis.jahr.theboringgame.controllers

import com.jannis.jahr.theboringgame.controllers.GameController.NoSuchPlayerException
import com.jannis.jahr.theboringgame.controllers.GameController.PlayerExistsException
import com.jannis.jahr.theboringgame.controllers.GameController.UsernameInvalidException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@EnableWebMvc
@ControllerAdvice
class GameExceptionController : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(NoSuchPlayerException::class)])
    fun handlePlayerNotFound(ex : NoSuchPlayerException, request : WebRequest) : ResponseEntity<ExceptionResponse> {
        val obj = ExceptionResponse()
        obj.errorMessage = ex.message!!
        obj.requestedURI = request.getDescription(false)
        return ResponseEntity<ExceptionResponse>(obj, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(PlayerExistsException::class)])
    fun handlePlayerExists(ex : PlayerExistsException, request : WebRequest) : ResponseEntity<ExceptionResponse> {
        val obj = ExceptionResponse()
        obj.errorMessage = ex.message!!
        obj.requestedURI = request.getDescription(false)
        return ResponseEntity<ExceptionResponse>(obj, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [(UsernameInvalidException::class)])
    fun handleUsernameInvalid(ex : UsernameInvalidException, request : WebRequest) : ResponseEntity<ExceptionResponse> {
        val obj = ExceptionResponse()
        obj.errorMessage = ex.message!!
        obj.requestedURI = request.getDescription(false)
        return ResponseEntity<ExceptionResponse>(obj, HttpStatus.BAD_REQUEST)
    }
}