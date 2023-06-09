package com.tesis.payticket.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Getter
@Setter
@Entity
@Table(name = "localidad")
public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String descripcion;

    @NotNull
    private int entradas;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Precio precio;

    @ManyToOne
    private Evento evento;

}