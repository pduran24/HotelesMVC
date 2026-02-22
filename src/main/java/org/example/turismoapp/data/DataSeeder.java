package org.example.turismoapp.data;

import lombok.extern.slf4j.Slf4j;
import org.example.turismoapp.model.*;
import org.example.turismoapp.repository.*;
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
    private final RutaRepository rutaRepository;

    public DataSeeder(HotelRepository hotelRepository, UserRepository userRepository, ClienteRepository clienteRepository, ReservaRepository reservaRepository, RutaRepository rutaRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.rutaRepository = rutaRepository;
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
        crearImagenes();
        crearRutas();
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
                buildHotel("Isaba Hotel", "Valle del Roncal", "Apartamentos turísticos ideales para esquí de fondo y senderismo por la Selva de Irati.", 3, "80.00", 42.8605, -0.9232)
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

    private void crearImagenes() {
        // --- VALLE DE ARÁN ---
        agregarImagenesHotel("Gran Hotel La Florida", "florida", "la_florida", "Vista del Gran Hotel La Florida",4);
        agregarImagenesHotel("Parador de Vielha", "vielha", "vielha", "Vista del Parador de Vielha",4);
        agregarImagenesHotel("Hotel Val de Neu", "val_de_neu", "val_de_neu", "Instalaciones del Hotel Val de Neu",4);
        agregarImagenesHotel("Refugio Rosta", "rosta", "rosta", "Interior del Refugio Rosta",4);
        agregarImagenesHotel("Posada Real de Santa Maria", "santa_maria", "santa_maria", "Exterior de la Posada",4);
        agregarImagenesHotel("Camping Verneda", "verneda", "verneda", "Bungalows del Camping Verneda",4);

        // --- PIRINEO ARAGONÉS ---
        agregarImagenesHotel("Gran Hotel Balneario de Panticosa", "balneario_panticosa", "balneario_panticosa", "Instalaciones del Balneario",4);
        agregarImagenesHotel("Balneario de Panticosa Continental", "continental_panticosa", "continental_panticosa", "Diseño interior del Continental",4);
        agregarImagenesHotel("Hotel Ciria", "ciria", "ciria", "Ambiente del Hotel Ciria", 4);
        agregarImagenesHotel("Hotel Sommos Aneto", "sommos_aneto", "sommos_aneto", "Vistas desde el Sommos Aneto", 4);
        agregarImagenesHotel("Barceló Monasterio de Boltaña", "monasterio_boltana", "monasterio_boltana", "Spa del Monasterio de Boltaña", 4);
        agregarImagenesHotel("Refugio de Góriz", "goriz", "goriz", "Vistas al Monte Perdido desde Góriz", 2);
        agregarImagenesHotel("Hotel Edelweiss", "edelweiss", "edelweiss", "Hotel Edelweiss a pie de pistas", 4);
        agregarImagenesHotel("Hotel & Spa Real Badaguás", "badaguas", "badaguas", "Campo de golf del Real Badaguás", 2);
        agregarImagenesHotel("Casa Rural El Callizo", "callizo", "callizo", "Encanto rural de El Callizo", 2);
        agregarImagenesHotel("Hotel La Casueña", "casuena", "casuena", "Vistas desde La Casueña", 4);
        agregarImagenesHotel("Albergue de Canfranc", "canfranc", "canfranc", "Instalaciones del Albergue", 2);
        agregarImagenesHotel("Hotel Tierra de Biescas", "tierra_biescas", "tierra_biescas", "Jardines de Tierra de Biescas", 2);
        agregarImagenesHotel("Refugio de Cap de Llauset", "llauset", "llauset", "El moderno refugio de Cap de Llauset", 2);

        // --- ANDORRA Y CATALUÑA ---
        agregarImagenesHotel("Sport Hotel Hermitage & Spa", "hermitage", "hermitage", "Lujo en el Sport Hotel Hermitage", 4);
        agregarImagenesHotel("Hotel Nordic", "nordic", "nordic", "Piscina del Hotel Nordic", 4);
        agregarImagenesHotel("Hotel Fontanals Golf", "fontanals", "fontanals", "Entorno del Fontanals Golf", 2);

        // --- PIRINEO NAVARRO Y OTROS ---
        agregarImagenesHotel("Hotel Roncesvalles", "roncesvalles", "roncesvalles", "Historia del Hotel Roncesvalles", 2);
        agregarImagenesHotel("Isaba Hotel", "isaba", "isaba", "Apartamentos del Isaba Hotel", 4);
    }

    /**
     * MÉTODO AUXILIAR: Añade exactamente 4 imágenes a un hotel si no tiene ninguna.
     * @param nombreHotel Nombre exacto del hotel en la BD.
     * @param carpeta Nombre de la carpeta dentro de static/images/
     * @param prefijo Prefijo de los archivos (ej: "goriz" buscará "goriz_1.jpg", "goriz_2.jpg"...)
     * @param altTextBase Texto alternativo base para accesibilidad.
     */
    private void agregarImagenesHotel(String nombreHotel, String carpeta, String prefijo, String altTextBase, int numFotos) {
        Hotel hotel = hotelRepository.findByNombre(nombreHotel).orElse(null);

        if (hotel != null && hotel.getImagenes().isEmpty()) {

            for (int i = 1; i <= numFotos; i++) {
                HotelImagen img = HotelImagen.builder()
                        .url("/images/" + carpeta + "/" + prefijo + "_" + i + ".jpg")
                        .textoAlternativo(altTextBase + " - Foto " + i)
                        .hotel(hotel)
                        .build();

                hotel.getImagenes().add(img);
            }

            hotelRepository.save(hotel);
            log.info("📸 {} imágenes cargadas para: {}", numFotos, nombreHotel);
        }
    }

    private void crearRutas() {
        if (rutaRepository.count() == 0) {
            System.out.println("Cargando rutas de senderismo en la base de datos...");

            List<Ruta> listaRutas = List.of(
                    new Ruta("Cola de Caballo (Ordesa)", "Moderada", 17.5, 450, 42.6416, -0.0566, "warning",
                            "Impresionante recorrido por el fondo del Valle de Ordesa hasta la famosa cascada. Disfruta de un paisaje protegido lleno de bosques, cascadas y paredes verticales.",
                            List.of("https://picsum.photos/seed/cola1/800/600", "https://picsum.photos/seed/cola2/800/600")),

                    new Ruta("Ascenso al Aneto", "Extrema", 14.5, 1500, 42.6788, 0.6558, "danger",
                            "Ruta mítica al techo de los Pirineos. Atraviesa el glaciar más grande de la cordillera y corona la cima tras superar el famoso y expuesto Paso de Mahoma.",
                            List.of("https://picsum.photos/seed/aneto1/800/600", "https://picsum.photos/seed/aneto2/800/600")),

                    new Ruta("Ruta de las Pasarelas del Vero", "Fácil", 3.2, 150, 42.1741, 0.0264, "success",
                            "Agradable paseo apto para familias por pasarelas suspendidas sobre el río Vero, descubriendo el cañón calcáreo y la hermosa villa medieval de Alquézar.",
                            List.of("https://picsum.photos/seed/vero1/800/600", "https://picsum.photos/seed/vero2/800/600")),

                    new Ruta("Ibón de Plan (Basa de la Mora)", "Moderada", 4.5, 300, 42.5486, 0.3168, "warning",
                            "Excursión a uno de los lagos glaciares más hermosos y mágicos del Pirineo, rodeado de praderas verdes, pino negro y altivas cumbres rocosas.",
                            List.of("https://picsum.photos/seed/plan1/800/600", "https://picsum.photos/seed/plan2/800/600")),

                    new Ruta("3 Ibones de Batisielles", "Difícil", 12.0, 750, 42.6669, 0.5055, "danger",
                            "Ruta exigente pero inmensamente gratificante que enlaza tres impresionantes ibones glaciares en el corazón del Parque Natural Posets-Maladeta.",
                            List.of("https://picsum.photos/seed/bati1/800/600", "https://picsum.photos/seed/bati2/800/600")),

                    new Ruta("Monte Perdido desde Pradera de Ordesa", "Extrema", 21.0, 1700, 42.6743, 0.0345, "danger",
                            "Dura ascensión a una de las cumbres más emblemáticas, pasando por las Gradas de Soaso y la mítica y peligrosa Escupidera antes de la cima.",
                            List.of("https://picsum.photos/seed/perdido1/800/600", "https://picsum.photos/seed/perdido2/800/600")),

                    new Ruta("Lagos de Ayous", "Moderada", 14.0, 800, 42.8655, -0.4382, "warning",
                            "Magnífica ruta circular en la vertiente francesa del Parque Nacional de los Pirineos, ofreciendo el mejor mirador posible del imponente Pic du Midi d'Ossau.",
                            List.of("https://picsum.photos/seed/ayous1/800/600", "https://picsum.photos/seed/ayous2/800/600")),

                    new Ruta("Circo de Gavarnie", "Fácil", 8.0, 200, 42.7361, -0.0104, "success",
                            "Paseo accesible hacia el espectacular anfiteatro rocoso declarado Patrimonio de la Humanidad, rematado por una de las cascadas más altas de Europa.",
                            List.of("https://picsum.photos/seed/gavarnie1/800/600", "https://picsum.photos/seed/gavarnie2/800/600")),

                    new Ruta("Pic du Midi d’Ossau (vuelta completa)", "Difícil", 17.0, 900, 42.8353, -0.4325, "danger",
                            "Vuelta completa a esta icónica y fotogénica montaña de origen volcánico, recorriendo diversos refugios, collados y paisajes pastoriles.",
                            List.of("https://picsum.photos/seed/midi1/800/600", "https://picsum.photos/seed/midi2/800/600")),

                    new Ruta("Ruta al Balneario de Panticosa – Ibones Azules", "Moderada", 11.5, 700, 42.7609, -0.2336, "warning",
                            "Clásica ascensión desde el histórico Balneario de Panticosa, subiendo por marmiteras de granito hasta alcanzar estos espectaculares ibones de alta montaña.",
                            List.of("https://picsum.photos/seed/panti1/800/600", "https://picsum.photos/seed/panti2/800/600")),

                    new Ruta("Refugio de Góriz desde Ordesa", "Difícil", 18.0, 900, 42.6634, 0.0340, "danger",
                            "Ruta de aproximación al refugio más pernoctado de España, puerta de entrada a las grandes cumbres calcáreas y punto clave de travesías como la Senda Pirenaica (GR11).",
                            List.of("https://picsum.photos/seed/goriz1/800/600", "https://picsum.photos/seed/goriz2/800/600")),

                    new Ruta("Estany de Sant Maurici – Cascada de Ratera", "Fácil", 6.5, 250, 42.5876, 1.0032, "success",
                            "Agradable ruta en el corazón del Parque Nacional de Aigüestortes. Disfruta de la icónica vista de Els Encantats reflejados en el lago y la atronadora cascada.",
                            List.of("https://picsum.photos/seed/maurici1/800/600", "https://picsum.photos/seed/maurici2/800/600")),

                    new Ruta("Carros de Foc (tramo corto)", "Difícil", 15.0, 1000, 42.6030, 0.9370, "danger",
                            "Tramo de alta dureza y extremada belleza de la mítica travesía que une los refugios de Aigüestortes a través de collados a más de 2400 metros de altitud.",
                            List.of("https://picsum.photos/seed/carros1/800/600", "https://picsum.photos/seed/carros2/800/600")),

                    new Ruta("Lago de Certascan", "Moderada", 9.5, 600, 42.6630, 1.2595, "warning",
                            "Ascensión inmersiva en la naturaleza virgen hacia el lago natural a mayor altitud y de mayores dimensiones de todo el Pirineo catalán.",
                            List.of("https://picsum.photos/seed/certa1/800/600", "https://picsum.photos/seed/certa2/800/600")),

                    new Ruta("Pico Posets desde Ángel Orús", "Extrema", 19.0, 1350, 42.6548, 0.4042, "danger",
                            "Exigente ascensión al segundo pico más alto de la cordillera, dominando visualmente el salvaje valle de Benasque y su sobrecogedora cresta de las Espadas.",
                            List.of("https://picsum.photos/seed/posets1/800/600", "https://picsum.photos/seed/posets2/800/600")),

                    new Ruta("Faja de Pelay (Ordesa)", "Difícil", 21.5, 1000, 42.6428, -0.0375, "danger",
                            "Espectacular y vertiginoso sendero colgado por lo alto de las paredes sur del valle de Ordesa, ofreciendo una perspectiva de pájaro inigualable del parque.",
                            List.of("https://picsum.photos/seed/pelay1/800/600", "https://picsum.photos/seed/pelay2/800/600")),

                    new Ruta("Selva de Irati – Embalse de Irabia", "Fácil", 7.0, 150, 42.9834, -1.1795, "success",
                            "Caminata tranquila y sombreada bordeando el embalse en el segundo hayedo-abetal más extenso y mejor conservado de Europa, especialmente mágico en otoño.",
                            List.of("https://picsum.photos/seed/irati1/800/600", "https://picsum.photos/seed/irati2/800/600")),

                    new Ruta("Pico Midi d’Ossau (ascenso clásico)", "Extrema", 10.0, 1200, 42.8353, -0.4325, "danger",
                            "Ruta altamente técnica. Escalar este antiguo volcán requiere trepadas de grado II/III y destreza en rapel, reservada exclusivamente para expertos.",
                            List.of("https://picsum.photos/seed/midiasc1/800/600", "https://picsum.photos/seed/midiasc2/800/600")),

                    new Ruta("Camino al Ibón de Piedrafita", "Moderada", 9.0, 500, 42.7354, -0.2952, "warning",
                            "Ruta sencilla, muy frecuentada e ideal para iniciarse en la montaña a los pies de la imponente Peña Telera, transcurriendo entre pastos y bosques.",
                            List.of("https://picsum.photos/seed/piedra1/800/600", "https://picsum.photos/seed/piedra2/800/600")),

                    new Ruta("Valle de Benasque – Forau de Aigualluts", "Fácil", 5.5, 200, 42.6901, 0.6410, "success",
                            "Excursión imprescindible y mágica. Contempla cómo las aguas de los glaciares desaparecen en un inmenso sumidero kárstico frente a la majestuosa vista del Aneto.",
                            List.of("https://picsum.photos/seed/aigu1/800/600", "https://picsum.photos/seed/aigu2/800/600"))
            );

            rutaRepository.saveAll(listaRutas);
        }
    }
}
