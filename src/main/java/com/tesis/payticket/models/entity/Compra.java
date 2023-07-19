package com.tesis.payticket.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String transaccion;

    private double monto;

    private int cantidad;

    private Date fechaTransaccion;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;

    @OneToOne
    @JoinColumn(name = "boleto_id")
    private Boleto boleto;


}