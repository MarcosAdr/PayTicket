package com.tesis.payticket.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "promotor")
public class Promotor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nombre;

    @Email
    private String email;

    private String telefono;

    private String direccion;

    private String tipoEvento;

    @OneToMany(mappedBy = "promotor")
    private List<Usuario> usuarios;

}