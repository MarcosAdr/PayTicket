package com.tesis.payticket.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tipo_evento")
public class TipoEvento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @UniqueElements
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "tipoEvento", fetch = FetchType.LAZY)//cascade me dice si borro un tipo de evento se borrara totos los eventos y eso no queremos
    private List<Evento> eventos;

    @Column(name = "date_created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public void addEvento(Evento evento){
        eventos.add(evento);
    }

    @PrePersist
    public void prePersist() {
        dateCreated = new Date();
        lastUpdate = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdate = new Date();
    }

    public TipoEvento() {
        eventos = new ArrayList<Evento>();
    }
}