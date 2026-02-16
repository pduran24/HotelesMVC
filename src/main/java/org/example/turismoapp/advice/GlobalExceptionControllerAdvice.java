package org.example.turismoapp.advice;

import org.example.turismoapp.exception.UsuarioYaExistenteException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalExceptionControllerAdvice {

    @ExceptionHandler(UsuarioYaExistenteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String manejarUsuarioExistente (UsuarioYaExistenteException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());

        return "registro";
    }

}
