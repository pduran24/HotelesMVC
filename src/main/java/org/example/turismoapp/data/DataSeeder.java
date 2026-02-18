package org.example.turismoapp.data;

import lombok.extern.slf4j.Slf4j;
import org.example.turismoapp.model.Cliente;
import org.example.turismoapp.model.Hotel;
import org.example.turismoapp.model.Reserva;
import org.example.turismoapp.model.UserEntity;
import org.example.turismoapp.repository.ClienteRepository;
import org.example.turismoapp.repository.HotelRepository;
import org.example.turismoapp.repository.ReservaRepository;
import org.example.turismoapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;

    public DataSeeder(HotelRepository hotelRepository, UserRepository userRepository, ClienteRepository clienteRepository, ReservaRepository reservaRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (hotelRepository.count() == 0) {
            log.info("Iniciando carga de datos: Destino Pirineos...");
            crearHoteles();
        }

        if (clienteRepository.count() == 0) {
            crearClientes();
        }

        crearFavoritos();

        if (userRepository.count() == 0) {
            crearAdmin();
        }
        crearUsuariosLogin();
        crearReservaPrueba();
    }


    private void crearHoteles() {
        List<Hotel> hoteles = List.of(
                // --- VALLE DE ARÁN (Lleida) ---
                buildHotel("Gran Hotel La Florida", "Baqueira Beret", "Lujo a pie de pistas en la cota 1500. Spa de aguas termales, gastronomía aranesa y servicio de guardaesquís exclusivo.", 5, "350.00", 42.6990, 0.9333),
                buildHotel("Parador de Vielha", "Vielha", "Vistas panorámicas al Valle de Arán. Spa circular acristalado y arquitectura típica de montaña. Ideal para desconectar.", 4, "180.50", 42.7051, 0.7963),
                buildHotel("Hotel Val de Neu", "Baqueira", "Elegancia y diseño moderno. Habitaciones con chimenea y vistas a la montaña. Servicio 5 estrellas Gran Lujo.", 5, "420.00", 42.7005, 0.9320),
                buildHotel("Refugio Rosta", "Salardú", "El hotel más antiguo del Valle. Ambiente rústico, madera crujiente y trato familiar. Historia viva del pirineismo.", 2, "60.00", 42.7072, 0.9023),
                buildHotel("Posada Real de Santa Maria", "Unha", "Pequeña posada con encanto en uno de los pueblos más bonitos. Trato muy personal.", 2, "65.00", 42.7093, 0.9035),
                buildHotel("Camping Verneda", "Pont d'Arròs", "Bungalows de madera en pleno bosque. Sonido del río Garona constante. Perfecto para verano.", 2, "55.00", 42.7335, 0.7652),

                // --- PIRINEO ARAGONÉS (Huesca) ---
                buildHotel("Gran Hotel Balneario de Panticosa", "Panticosa", "Edificio histórico del siglo XIX rodeado de picos de 3000m. Aguas medicinales y lujo clásico.", 4, "210.00", 42.7601, -0.2372),
                buildHotel("Balneario de Panticosa Continental", "Panticosa", "Diseño de Rafael Moneo. Modernidad integrada en la naturaleza. Circuito termal incluido.", 4, "150.00", 42.7598, -0.2365),
                buildHotel("Hotel Ciria", "Benasque", "Referente en el valle. Arquitectura en piedra y madera, famoso por su cocina de caza y setas. Ambiente de montañeros.", 3, "95.00", 42.6062, 0.5228),
                buildHotel("Hotel Sommos Aneto", "Benasque", "Diseño en madera, vistas al río Ésera. Tienda de material de montaña en el propio hotel.", 4, "140.00", 42.6055, 0.5236),
                // He actualizado la descripción de Boltaña como pediste para resaltar el Spa
                buildHotel("Barceló Monasterio de Boltaña", "Boltaña", "Antiguo monasterio del siglo XVII reformado. Spa completo, aguas terapéuticas y piscina exterior enorme entre montañas.", 5, "190.00", 42.4431, 0.0652),
                buildHotel("Refugio de Góriz", "Parque Nacional Ordesa", "Solo accesible a pie (4h). A los pies del Monte Perdido. Literas, cena comunitaria y el cielo más estrellado.", 1, "25.00", 42.6635, 0.0412),
                buildHotel("Hotel Edelweiss", "Candanchú", "A 50 metros del telesilla. Ideal para familias y cursillos de esquí. Sencillo pero con todo lo necesario.", 3, "85.00", 42.7885, -0.5270),
                buildHotel("Hotel & Spa Real Badaguás", "Jaca", "Complejo moderno con campo de golf y spa. Vistas a la Peña Oroel. Perfecto para combinar deporte y relax.", 4, "115.00", 42.5852, -0.4895),
                buildHotel("Casa Rural El Callizo", "Aínsa", "En el corazón de la villa medieval. Muros de piedra, desayunos caseros y vistas a la Peña Montañesa.", 3, "75.00", 42.4165, 0.1382),
                buildHotel("Hotel La Casueña", "Lanuza", "A orillas del embalse. Vistas espectaculares a Foratata. Pequeño, íntimo y con un restaurante exquisito.", 3, "105.00", 42.7568, -0.3165),
                buildHotel("Albergue de Canfranc", "Canfranc-Estación", "Económico y funcional. Punto de encuentro de peregrinos del Camino de Santiago y esquiadores.", 1, "22.00", 42.7505, -0.5142),
                buildHotel("Hotel Tierra de Biescas", "Biescas", "Diseño nórdico en el Pirineo Aragonés. Jardines amplios y piscina climatizada todo el año.", 4, "130.00", 42.6285, -0.3228),
                buildHotel("Refugio de Cap de Llauset", "Montanuy", "El refugio más moderno del Pirineo. Arquitectura vanguardista a 2400m de altura.", 1, "30.00", 42.5892, 0.6975),

                // --- ANDORRA Y CATALUÑA (Cerdanya) ---
                buildHotel("Sport Hotel Hermitage & Spa", "Soldeu", "El único Leading Hotel of the World en Andorra. Acceso directo al Sport Wellness Mountain Spa.", 5, "550.00", 42.5765, 1.6645),
                buildHotel("Hotel Nordic", "El Tarter", "Hotel clásico a pie de pistas. Habitaciones de madera, bolera y piscina interior. Muy familiar.", 4, "160.00", 42.5798, 1.6502),
                buildHotel("Hotel Fontanals Golf", "Cerdanya", "Rodeado de naturaleza y campos de golf. Ambiente tranquilo, ideal para escapadas de primavera y otoño.", 4, "120.00", 42.4045, 1.9052),

                // --- PIRINEO NAVARRO ---
                buildHotel("Hotel Roncesvalles", "Roncesvalles", "Antiguo hospital de peregrinos reformado. Historia medieval y confort moderno. Inicio del Camino Francés.", 3, "70.00", 43.0092, -1.3195),
                buildHotel("Isaba Hotel", "Valle del Roncal", "Apartamentos turísticos ideales para esquí de fondo y senderismo por la Selva de Irati.", 3, "80.00", 42.8605, -0.9232),

                // --- EJEMPLO SIN UBICACIÓN EXACTA (Para probar nulls) ---
                buildHotel("Hostal de Prueba", "Pirineo Desconocido", "Ejemplo para probar el mapa sin coordenadas.", 2, "40.00", null, null)

        );
        hotelRepository.saveAll(hoteles);
        log.info("Hoteles cargados correctamente.");
    }

    private void crearClientes() {
        // CLIENTE 1: Pablo
        Cliente c1 = new Cliente();
        c1.setNombre("Pablo Viajero");
        c1.setEmail("pablo@test.com");
        c1.setFavoritos(new java.util.HashSet<>());

        // CLIENTE 2: Ana
        Cliente c2 = new Cliente();
        c2.setNombre("Ana Montañera");
        c2.setEmail("ana@test.com");
        c2.setFavoritos(new java.util.HashSet<>());

        clienteRepository.saveAll(List.of(c1, c2));
        log.info("Clientes ficticios creados con email.");
    }

    private void crearFavoritos() {
        if (clienteRepository.count() == 0) return;

        Cliente pablo = clienteRepository.findAll().getFirst();

        Hotel laFlorida = hotelRepository.findByNombre("Gran Hotel La Florida").orElse(null);
        Hotel goriz = hotelRepository.findByNombre("Refugio de Góriz").orElse(null);

        if (laFlorida != null && goriz != null) {
            pablo.getFavoritos().add(laFlorida);
            pablo.getFavoritos().add(goriz);

            clienteRepository.save(pablo);
            log.info("✅ Favoritos inicializados: A Pablo le gustan 2 hoteles.");
        }
    }

    // Inyecta ReservaRepository arriba en el constructor del DataSeeder

    private void crearReservaPrueba() {
        if (reservaRepository.count() == 0) {
            Cliente laura = clienteRepository.findByEmail("pablo@test.com").orElse(null); // Asegúrate de que existe
            Hotel hotel = hotelRepository.findByNombre("Gran Hotel La Florida").orElse(null);

            if (laura != null && hotel != null) {
                Reserva r = new Reserva();
                r.setCliente(laura);
                r.setHotel(hotel);
                r.setFechaEntrada(java.time.LocalDate.now().plusDays(10));
                r.setFechaSalida(java.time.LocalDate.now().plusDays(15));
                r.setPrecioTotal(new java.math.BigDecimal("1500.00"));
                r.setEstado(org.example.turismoapp.model.enums.EstadoReserva.CONFIRMADA);

                reservaRepository.save(r);
                log.info("🎁 ¡Reserva de regalo creada para Pablo!");
            }
        }
    }

    private void crearAdmin() {
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setRole("ADMIN");
        userRepository.save(admin);
        log.info("Usuario ADMIN creado.");
    }

    private Hotel buildHotel(String nombre, String ubicacion, String descripcion, Integer estrellas, String precio, Double latitud, Double longitud) {
        Hotel hotel = new Hotel();
        hotel.setNombre(nombre);
        hotel.setUbicacion(ubicacion);
        hotel.setDescripcion(descripcion);
        hotel.setEstrellas(estrellas);
        hotel.setPrecioNoche(new BigDecimal(precio));
        hotel.setLatitud(latitud);
        hotel.setLongitud(longitud);
        hotel.setImagenes(new java.util.ArrayList<>());
        hotel.setResenas(new java.util.ArrayList<>());
        return hotel;
    }

    private void crearUsuariosLogin() {
        // Usuario para Pablo
        if (!userRepository.existsByUsername("pablo@test.com")) {
            UserEntity userPablo = new UserEntity();
            userPablo.setUsername("pablo@test.com");
            userPablo.setPassword("1234"); // En un caso real, iría encriptada
            userPablo.setRole("USER");
            userRepository.save(userPablo);
        }

        // Usuario Admin
        if (!userRepository.existsByUsername("admin")) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
        log.info("Usuarios de seguridad (Login) creados.");
    }
}
