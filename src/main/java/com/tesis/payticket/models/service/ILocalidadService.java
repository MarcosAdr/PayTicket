package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Localidad;

import java.util.List;

public interface ILocalidadService {

    public List<Localidad> findAll();

    public void save(Localidad localidad);

    public Localidad findOne(Long id);

    public void delete(Long id);

}
