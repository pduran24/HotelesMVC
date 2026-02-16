package org.example.turismoapp.data;

import lombok.extern.slf4j.Slf4j;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.model.UserEntity;
import org.example.turismoapp.repository.HotelRepository;
import org.example.turismoapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Componente encargado de cargar datos iniciales en la base de datos al arrancar la aplicación.
 * Útil para pruebas y desarrollo.
 */
@Component
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param hotelRepository Repositorio de hoteles.
     * @param userRepository Repositorio de usuarios.
     */
    public DataSeeder(HotelRepository hotelRepository, UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    /**
     * Método que se ejecuta al iniciar la aplicación.
     * Carga hoteles y usuarios si las tablas están vacías.
     *
     * @param args Argumentos de línea de comandos.
     * @throws Exception si ocurre algún error durante la carga de datos.
     */
    @Override
    public void run(String... args) throws Exception {
        // 1. Carga de Hoteles (Si está vacía)
        if (hotelRepository.count() == 0) {
            log.info("Iniciando carga de datos: Destino Pirineos...");

            List<Hotel> hoteles = List.of(
                    buildHotel("Gran Hotel La Florida", "Baqueira Beret", "Lujo a pie de pistas en la cota 1500. Spa de aguas termales, gastronomía aranesa y servicio de guardaesquís exclusivo.", 5, "350.00"),
                    buildHotel("Parador de Vielha", "Vielha", "Vistas panorámicas al Valle de Arán. Spa circular acristalado y arquitectura típica de montaña. Ideal para desconectar.", 4, "180.50"),
                    buildHotel("Hotel Val de Neu", "Baqueira", "Elegancia y diseño moderno. Habitaciones con chimenea y vistas a la montaña. Servicio 5 estrellas Gran Lujo.", 5, "420.00"),
                    buildHotel("Refugio Rosta", "Salardú", "El hotel más antiguo del Valle. Ambiente rústico, madera crujiente y trato familiar. Historia viva del pirineismo.", 2, "60.00"),

                    buildHotel("Gran Hotel Balneario de Panticosa", "Panticosa", "Edificio histórico del siglo XIX rodeado de picos de 3000m. Aguas medicinales y lujo clásico.", 4, "210.00"),
                    buildHotel("Hotel Ciria", "Benasque", "Referente en el valle. Arquitectura en piedra y madera, famoso por su cocina de caza y setas. Ambiente de montañeros.", 3, "95.00"),
                    buildHotel("Barceló Monasterio de Boltaña", "Boltaña", "Antiguo monasterio del siglo XVII reformado. Situado a las puertas de Ordesa. Paz, claustros y piscina exterior enorme.", 5, "190.00"),
                    buildHotel("Refugio de Góriz", "Parque Nacional Ordesa", "Solo accesible a pie (4h). A los pies del Monte Perdido. Literas, cena comunitaria y el cielo más estrellado.", 1, "25.00"),
                    buildHotel("Hotel Edelweiss", "Candanchú", "A 50 metros del telesilla. Ideal para familias y cursillos de esquí. Sencillo pero con todo lo necesario.", 3, "85.00"),
                    buildHotel("Hotel & Spa Real Badaguás", "Jaca", "Complejo moderno con campo de golf y spa. Vistas a la Peña Oroel. Perfecto para combinar deporte y relax.", 4, "115.00"),
                    buildHotel("Casa Rural El Callizo", "Aínsa", "En el corazón de la villa medieval. Muros de piedra, desayunos caseros y vistas a la Peña Montañesa.", 3, "75.00"),
                    buildHotel("Hotel La Casueña", "Lanuza", "A orillas del embalse. Vistas espectaculares a Foratata. Pequeño, íntimo y con un restaurante exquisito.", 3, "105.00"),
                    buildHotel("Albergue de Canfranc", "Canfranc-Estación", "Económico y funcional. Punto de encuentro de peregrinos del Camino de Santiago y esquiadores.", 1, "22.00"),
                    buildHotel("Hotel Tierra de Biescas", "Biescas", "Diseño nórdico en el Pirineo Aragonés. Jardines amplios y piscina climatizada todo el año.", 4, "130.00"),

                    buildHotel("Sport Hotel Hermitage & Spa", "Soldeu", "El único Leading Hotel of the World en Andorra. Acceso directo al Sport Wellness Mountain Spa.", 5, "550.00"),
                    buildHotel("Hotel Fontanals Golf", "Cerdanya", "Rodeado de naturaleza y campos de golf. Ambiente tranquilo, ideal para escapadas de primavera y otoño.", 4, "120.00"),
                    buildHotel("Refugio de Cap de Llauset", "Montanuy", "El refugio más moderno del Pirineo. Arquitectura vanguardista a 2400m de altura.", 1, "30.00"),
                    buildHotel("Hotel Nordic", "El Tarter", "Hotel clásico a pie de pistas. Habitaciones de madera, bolera y piscina interior. Muy familiar.", 4, "160.00"),
                    buildHotel("Camping Verneda", "Pont d'Arròs", "Bungalows de madera en pleno bosque. Sonido del río Garona constante. Perfecto para verano.", 2, "55.00"),

                    buildHotel("Hotel Roncesvalles", "Roncesvalles", "Antiguo hospital de peregrinos reformado. Historia medieval y confort moderno. Inicio del Camino Francés.", 3, "70.00"),
                    buildHotel("Isaba Hotel", "Valle del Roncal", "Apartamentos turísticos ideales para esquí de fondo y senderismo por la Selva de Irati.", 3, "80.00"),
                    buildHotel("Balneario de Panticosa Continental", "Panticosa", "Diseño de Rafael Moneo. Modernidad integrada en la naturaleza. Circuito termal incluido.", 4, "150.00"),
                    buildHotel("Posada Real de Santa Maria", "Unha", "Pequeña posada con encanto en uno de los pueblos más bonitos. Trato muy personal.", 2, "65.00"),
                    buildHotel("Hotel Sommos Aneto", "Benasque", "Diseño en madera, vistas al río Ésera. Tienda de material de montaña en el propio hotel.", 4, "140.00"),

                    buildHotel("Hostal de Prueba Sin Descripción", "Pirineo", null, 2, "40.00")
            );

            hotelRepository.saveAll(hoteles);
            log.info("{} Hoteles del Pirineo cargados correctamente.", hoteles.size());
        }

        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");

            userRepository.save(admin);
            log.info("Usuario ADMIN creado: admin / admin123");
        }
    }

    /**
     * Método auxiliar para construir objetos Hotel.
     *
     * @param nombre Nombre del hotel.
     * @param ubicacion Ubicación del hotel.
     * @param descripcion Descripción del hotel.
     * @param estrellas Número de estrellas.
     * @param precio Precio por noche como String.
     * @return Objeto Hotel construido.
     */
    private Hotel buildHotel(String nombre, String ubicacion, String descripcion, Integer estrellas, String precio) {
        Hotel hotel = new Hotel();
        hotel.setNombre(nombre);
        hotel.setUbicacion(ubicacion);
        hotel.setDescripcion(descripcion);
        hotel.setEstrellas(estrellas);
        hotel.setPrecioNoche(new BigDecimal(precio));
        return hotel;
    }
}
