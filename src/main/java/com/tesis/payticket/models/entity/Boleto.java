package com.tesis.payticket.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "boleto")
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String codigoQR;

    private Boolean entregado;

    @OneToOne(mappedBy = "boleto")
    private Compra compra;

    @Column(name = "date_created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @PrePersist
    public void prePersist() {
        dateCreated = new Date();
    }

}