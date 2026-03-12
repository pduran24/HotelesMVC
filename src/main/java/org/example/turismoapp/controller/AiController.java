package org.example.turismoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.service.ChatAiService;
import org.example.turismoapp.service.VectorizacionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class AiController {

    private final ChatAiService chatAiService;
    private final VectorizacionService vectorizacionService;


    @GetMapping("/asistente")
    public String hablarConConserje(
            @RequestParam(defaultValue = "usuario_anonimo") String sessionId,
            @RequestParam String mensaje) {

        return chatAiService.hablarConElConserje(sessionId, mensaje);
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/limpiar")
    public org.springframework.http.ResponseEntity<Void> limpiarConversacion(@RequestParam String sessionId) {
        chatAiService.limpiarMemoria(sessionId);
        return org.springframework.http.ResponseEntity.ok().build();
    }

    @org.springframework.web.bind.annotation.GetMapping("/admin/ingestar")
    public org.springframework.http.ResponseEntity<String> ingestarDatos() {
        vectorizacionService.cargarHotelesEnMemoriaVectorial();
        return org.springframework.http.ResponseEntity.ok("Base de datos vectorial actualizada con éxito.");
    }
}