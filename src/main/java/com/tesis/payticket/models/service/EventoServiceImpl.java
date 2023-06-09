package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.IEventoDao;
import com.tesis.payticket.models.dao.ILocalidadDao;
import com.tesis.payticket.models.entity.Evento;
import com.tesis.payticket.models.entity.Localidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoServiceImpl implements IEventoService {

    @Autowired
    private IEventoDao eventoDao;

    @Autowired
    private ILocalidadDao localidadDao;

    @Override
    @Transactional(readOnly = true)
    public List<Evento> findAll() {
        return (List<Evento>) eventoDao.findAll();
    }

    @Override
    @Transactional
    public void save(Evento evento) {
        eventoDao.save(evento);
    }

    @Override
    @Transactional(readOnly = true)
    public Evento findOne(Long id) {
        return eventoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        eventoDao.deleteById(id);
    }
    @Override
    @Transactional
    public void saveLocalidad(Localidad localidad) {
        localidadDao.save(localidad);
    }
}
