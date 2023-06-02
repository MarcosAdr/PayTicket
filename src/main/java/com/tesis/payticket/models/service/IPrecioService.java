package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Precio;

import java.util.List;

public interface IPrecioService {

    public List<Precio> findAll();

    public void save(Precio precio);

    public Precio findOne(Long id);

    public void delete(Long id);
}
