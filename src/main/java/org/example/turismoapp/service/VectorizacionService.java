package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VectorizacionService {

    private static final Logger log = LoggerFactory.getLogger(VectorizacionService.class);

    private final HotelRepository hotelRepository;
    private final VectorStore vectorStore; // Herramienta de Spring AI para guardar vectores

    /**
     * Este método lee todos los hoteles, los convierte a texto semántico y los guarda en PGVector.
     */
    public void cargarHotelesEnMemoriaVectorial() {
        log.info("Iniciando el proceso de vectorización de hoteles...");

        List<Hotel> hoteles = hotelRepository.findAll();
        List<Document> documentosAI = new ArrayList<>();

        for (Hotel hotel : hoteles) {
            // 1. Convertimos los datos fríos en un párrafo semántico que la IA pueda entender
            String textoSemantico = String.format(
                    "Refugio/Hotel: %s. Ubicado en: %s. Tiene %d estrellas. " +
                            "Precio por noche: %s euros. Admite mascotas: %s. Tiene zona de aguas o Spa: %s. " +
                            "Descripción detallada: %s",
                    hotel.getNombre(),
                    hotel.getUbicacion(),
                    hotel.getEstrellas(),
                    hotel.getPrecioNoche(),
                    hotel.isAdmiteMascotas() ? "Sí" : "No",
                    hotel.isTieneSpa() ? "Sí" : "No",
                    hotel.getDescripcion()
            );

            // 2. Metadatos: Claves extra que guardamos junto al vector para facilitar el filtrado después
            Map<String, Object> metadatos = Map.of(
                    "hotel_id", hotel.getId(),
                    "nombre", hotel.getNombre(),
                    "ubicacion", hotel.getUbicacion()
            );

            // 3. Creamos el Documento de Spring AI
            Document doc = new Document(textoSemantico, metadatos);
            documentosAI.add(doc);
        }

        // 4. ¡Magia! Guardamos todos los documentos en la base de datos.
        // Spring AI, por debajo, usa el modelo local de Transformers para convertir el texto en números de 384 dimensiones.
        vectorStore.add(documentosAI);

        log.info("✅ ¡Proceso terminado! Se han vectorizado {} hoteles.", documentosAI.size());
    }
}