package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Ubicacion;

import java.util.List;

public interface IUbicacionService {

    public List<Ubicacion> findAll();

    public void save(Ubicacion ubicacion);

    public Ubicacion findOne(Long id);

    public void delete(Long id);


}
