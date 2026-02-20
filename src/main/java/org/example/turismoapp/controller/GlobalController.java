package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.repository.ReservaRepository;
import org.example.turismoapp.service.ReservaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@ControllerAdvice
@RequiredArgsConstructor
class GlobalController {
    private final ReservaRepository reservaRepository;

    @ModelAttribute("numReservas")
    public Long cargarNumeroReservas(Principal principal) {

        if (principal == null) {
            return 0L;
        }


        return reservaRepository.countByCliente_Email(principal.getName());
    }
}
