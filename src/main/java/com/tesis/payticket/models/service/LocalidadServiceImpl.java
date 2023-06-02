package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.ILocalidadDao;
import com.tesis.payticket.models.entity.Localidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocalidadServiceImpl implements ILocalidadService{

    @Autowired
    private ILocalidadDao localidadDao;

    @Override
    @Transactional(readOnly = true)
    public List<Localidad> findAll() {
        return (List<Localidad>) localidadDao.findAll();
    }

    @Override
    @Transactional
    public void save(Localidad localidad) {
        localidadDao.save(localidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Localidad findOne(Long id) {
        return localidadDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        localidadDao.deleteById(id);
    }
}
