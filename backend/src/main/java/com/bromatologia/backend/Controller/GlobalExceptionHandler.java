package com.bromatologia.backend.Controller;

import com.bromatologia.backend.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //clase para tomar las excepciones desde el lado del frontend

    @ExceptionHandler(TitularException.class)
    public ResponseEntity<String> handleTitularException(TitularException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerEmpresaException(EmpresaException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerEstablecimientoException(EstablecimientoException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerMantenimientoException(MantenimientoException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerProductoException(ProductoException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerReciboException(ReciboException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerRegistroEstablecimientoException(RegistroEstablecimientoException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerRegistroProductoException(RegistroProductoException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerRegistroProductoEstablecimientoException(RegistroProductoEstablecimientoException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerTitularException(TitularException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerTramiteException(TramiteException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handlerUsuarioException(UsuarioException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
