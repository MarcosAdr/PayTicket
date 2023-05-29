package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.TipoEvento;

import java.util.List;

public interface ITipoEventoService {

    public List<TipoEvento> findAll();

    public void save(TipoEvento tipoEvento);

    public TipoEvento findOne(Long id);

    public void delete(Long id);
}
