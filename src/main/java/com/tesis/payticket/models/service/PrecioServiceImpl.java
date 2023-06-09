package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.IPrecioDao;
import com.tesis.payticket.models.entity.Precio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrecioServiceImpl implements IPrecioService {

    @Autowired
    private IPrecioDao precioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Precio> findAll() {
        return (List<Precio>) precioDao.findAll();
    }

    @Override
    @Transactional
    public void save(Precio precio) {
        precioDao.save(precio);
    }

    @Override
    public Precio findOne(Long id) {
        return precioDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        precioDao.deleteById(id);
    }
}
