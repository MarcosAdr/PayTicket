package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Compra;

import java.util.List;

public interface ICompraService {

    public List<Compra> findAll();

    public void save(Compra compra);

    public Compra findOne(Long id);

    public void delete(Long id);

    public List<Compra> findByUsuarioId(Long id);

}
