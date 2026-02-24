package org.example.turismoapp.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;
@Service
public class ChatAiService {

    private final ChatClient chatClient;

    public ChatAiService(ChatClient.Builder chatClientBuilder) {

        this.chatClient = chatClientBuilder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public String hablarConElConserje(String idConversacion, String mensajeUsuario) {

        String systemPrompt = "Eres un conserje montañero y guía experto del Pirineo llamado 'Nivalis'. " +
                "Trabajas para la aplicación TurismoApp. " +
                "Respondes siempre de forma amable, usando emojis de montaña (⛰️, 🏕️, 🎒). " +
                "Tus respuestas deben ser breves, concisas y directas al grano.";

        return this.chatClient.prompt()
                .system(systemPrompt)
                .user(mensajeUsuario)
                .advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, idConversacion))
                .call()
                .content();
    }


}