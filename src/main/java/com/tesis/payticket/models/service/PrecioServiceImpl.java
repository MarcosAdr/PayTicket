package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.IPrecioDao;
import com.tesis.payticket.models.entity.Precio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrecioServiceImpl implements IPrecioService{

    @Autowired
    private IPrecioDao precioDao;

    @Override
    public List<Precio> findAll() {
        return null;
    }

    @Override
    public void save(Precio precio) {

    }

    @Override
    public Precio findOne(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
