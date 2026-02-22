package org.example.turismoapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String UPLOAD_DIR = "uploads/avatars/";

    public String guardarAvatar(MultipartFile archivo) throws IOException {
        if (archivo.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String nombreOriginal = archivo.getOriginalFilename();
        String nombreUnico = UUID.randomUUID().toString() + "_" + nombreOriginal;

        Path rutaDestino = uploadPath.resolve(nombreUnico);
        try (InputStream inputStream = archivo.getInputStream()) {
            Files.copy(inputStream, rutaDestino, StandardCopyOption.REPLACE_EXISTING);
        }

        return "/uploads/avatars/" + nombreUnico;
    }
}