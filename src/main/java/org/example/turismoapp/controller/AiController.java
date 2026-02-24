package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.service.ChatAiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class AiController {

    private final ChatAiService chatAiService;


    @GetMapping("/asistente")
    public String hablarConConserje(
            @RequestParam(defaultValue = "usuario_anonimo") String sessionId,
            @RequestParam String mensaje) {

        return chatAiService.hablarConElConserje(sessionId, mensaje);
    }
}