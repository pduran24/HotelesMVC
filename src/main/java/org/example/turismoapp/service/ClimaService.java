package org.example.turismoapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.turismoapp.dto.ClimaDTO;
import org.example.turismoapp.dto.PronosticoDiarioDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ClimaService {

    private final RestTemplate restTemplate;

    public ClimaService() {
        this.restTemplate = new RestTemplate();
    }

    public ClimaDTO obtenerClimaActual(Double lat, Double lon) {
        if (lat == null || lon == null) {
            return null;
        }

        try {
            String url = String.format(Locale.US,
                    "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true",
                    lat, lon);

            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response != null && response.has("current_weather")) {
                JsonNode current = response.get("current_weather");
                double temp = current.get("temperature").asDouble();
                int weatherCode = current.get("weathercode").asInt();
                boolean isDay = current.get("is_day").asInt() == 1;

                return mapearCodigoClima(temp, weatherCode, isDay);
            }
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo obtener el clima - " + e.getMessage());
        }
        return null;
    }

    public List<PronosticoDiarioDTO> obtenerPronosticoSemanal(Double lat, Double lon) {
        if (lat == null || lon == null) {
            return List.of();
        }

        try {
            String url = String.format(Locale.US,
                    "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&daily=weathercode,temperature_2m_max,temperature_2m_min&timezone=auto",
                    lat, lon);

            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response != null && response.has("daily")) {
                JsonNode daily = response.get("daily");
                JsonNode times = daily.get("time");
                JsonNode maxTemps = daily.get("temperature_2m_max");
                JsonNode minTemps = daily.get("temperature_2m_min");
                JsonNode codes = daily.get("weathercode");

                List<PronosticoDiarioDTO> pronostico = new ArrayList<>();

                for (int i = 0; i < times.size(); i++) {
                    LocalDate fecha = LocalDate.parse(times.get(i).asText());
                    double max = maxTemps.get(i).asDouble();
                    double min = minTemps.get(i).asDouble();
                    int code = codes.get(i).asInt();

                    ClimaDTO base = mapearCodigoClima(0.0, code, true);

                    pronostico.add(new PronosticoDiarioDTO(fecha, max, min, base.descripcion(), base.iconoCss()));
                }
                return pronostico;
            }
        } catch (Exception e) {
            System.err.println("Aviso: No se pudo obtener el pronóstico - " + e.getMessage());
        }
        return List.of();
    }

    private ClimaDTO mapearCodigoClima(double temp, int code, boolean isDay) {
        String desc = "Despejado";
        String icon = isDay ? "bi-sun-fill text-warning" : "bi-moon-stars-fill text-light";

        if (code == 1 || code == 2 || code == 3) {
            desc = "Nublado";
            icon = isDay ? "bi-cloud-sun-fill text-warning" : "bi-cloud-moon-fill text-secondary";
        } else if (code >= 45 && code <= 48) {
            desc = "Niebla";
            icon = "bi-cloud-fog2-fill text-secondary";
        } else if (code >= 51 && code <= 67) {
            desc = "Lluvia";
            icon = "bi-cloud-rain-fill text-info";
        } else if (code >= 71 && code <= 77) {
            desc = "Nieve";
            icon = "bi-snow text-white";
        } else if (code >= 95 && code <= 99) {
            desc = "Tormenta";
            icon = "bi-cloud-lightning-rain-fill text-warning";
        }

        return new ClimaDTO(temp, desc, icon);
    }


}