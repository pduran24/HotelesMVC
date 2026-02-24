package org.example.turismoapp.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class AiController {

    private final ChatClient chatClient;

    public AiController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/prueba")
    public String probarCerebro(@RequestParam(defaultValue = "Dime un dato curioso sobre el Pirineo Aragonés en una sola frase.") String mensaje) {

        return chatClient.prompt()
                .user(mensaje)
                .call()
                .content();
    }
}