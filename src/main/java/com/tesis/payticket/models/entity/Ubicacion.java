package com.tesis.payticket.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "ubicacion")
public class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;

    private String direccion;

    private String ciudad;

    private Double latitud;

    private Double longitud;

    private String pais;

    private String lugar;

    @OneToMany(mappedBy = "ubicacion")
    private List<Evento> eventos;


    //https://www.youtube.com/watch?v=aehhc5bS4OU&list=PLVuxTcoZLJZ91viOcffW12GpKqoCo0oKo&index=1 video SpringSecurity

}