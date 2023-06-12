package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.IUbicacionDAO;
import com.tesis.payticket.models.entity.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    @Autowired
    private IUbicacionDAO ubicacionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Ubicacion> findAll() {
        return (List<Ubicacion>) ubicacionDao.findAll();
    }

    @Override
    @Transactional
    public void save(Ubicacion ubicacion) {
        ubicacionDao.save(ubicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Ubicacion findOne(Long id) {
        return ubicacionDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        ubicacionDao.deleteById(id);
    }
}
