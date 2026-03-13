package org.example.turismoapp.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class ChatAiService {

    private final ChatClient chatClient;
    private final InMemoryChatMemory chatMemory;
    private final VectorStore vectorStore;

    public ChatAiService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatMemory = new InMemoryChatMemory();
        this.vectorStore = vectorStore;

        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(this.chatMemory),

                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().topK(24).build())                )
                .build();
    }

    public String hablarConElConserje(String idConversacion, String mensajeUsuario) {

        String systemPrompt = "Eres un conserje montañero y guía experto del Pirineo llamado 'Nivalis'. " +
                "Trabajas para la aplicación TurismoApp. " +
                "Respondes siempre de forma amable, usando emojis de montaña (⛰️, 🏕️, 🎒). " +
                "MUY IMPORTANTE: Se te proporcionará información de los refugios de nuestra base de datos en el contexto. " +
                "Utiliza EXCLUSIVAMENTE esa información para recomendar hoteles. Si no encuentras un hotel que cumpla lo que pide el usuario en el contexto, dile que no tenemos nada con esas características. No te inventes nombres de hoteles.";

        return this.chatClient.prompt()
                .system(systemPrompt)
                .user(mensajeUsuario)
                .advisors(a -> a.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, idConversacion))
                .call()
                .content();
    }

    public void limpiarMemoria(String idConversacion) {
        this.chatMemory.clear(idConversacion);
    }

}