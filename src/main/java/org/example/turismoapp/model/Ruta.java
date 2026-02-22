package org.example.turismoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rutas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String dificultad;
    private Double distanciaKm;
    private Integer desnivelM;
    private Double latitud;
    private Double longitud;
    private String color;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ElementCollection
    @CollectionTable(name = "ruta_imagenes", joinColumns = @JoinColumn(name = "ruta_id"))
    @Column(name = "imagen_url")
    private List<String> imagenes;


    public Ruta(String nombre, String dificultad, Double distanciaKm, Integer desnivelM, Double latitud, Double longitud, String color, String descripcion, List<String> imagenes) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.distanciaKm = distanciaKm;
        this.desnivelM = desnivelM;
        this.latitud = latitud;
        this.longitud = longitud;
        this.color = color;
        this.descripcion = descripcion;
        this.imagenes = imagenes;
    }

}