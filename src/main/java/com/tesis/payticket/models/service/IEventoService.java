package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Evento;

import java.util.List;

public interface IEventoService {
    public List<Evento> findAll();

    public void save(Evento evento);

    public Evento findOne(Long id);

    public void delete(Long id);


}