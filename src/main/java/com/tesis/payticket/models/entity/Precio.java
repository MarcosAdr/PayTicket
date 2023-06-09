package com.tesis.payticket.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "precio")
public class Precio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private float precio;
    @OneToOne(mappedBy = "precio", fetch = FetchType.LAZY)
    private Localidad localidad;



}