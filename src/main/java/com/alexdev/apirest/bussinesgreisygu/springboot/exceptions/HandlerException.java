package com.alexdev.apirest.bussinesgreisygu.springboot.exceptions;


import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class HandlerException {
    final String NOT_FOUND_MESSAGE = "No se encontraron resultados.";
    final String ALREADY_EXIST_MESSAGE = "Ya existe este recurso en la base de datos.";

    @ExceptionHandler( NotFoundException.class )
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundResponse( NotFoundException nfe ) {
        return ErrorResponse.builder()
                .message( NOT_FOUND_MESSAGE )
                .build();
    }

    @ExceptionHandler( AlreadyExistException.class )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse notFoundResponse( AlreadyExistException aee ) {
        return ErrorResponse.builder()
                .message( ALREADY_EXIST_MESSAGE )
                .build();
    }

}
