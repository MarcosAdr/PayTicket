package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.ITipoEventoDao;
import com.tesis.payticket.models.entity.TipoEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoEventoServiceImpl implements ITipoEventoService{


    @Autowired
    private ITipoEventoDao tipoEventoDao;

    @Override
    @Transactional(readOnly = true)
    public List<TipoEvento> findAll() {
        return (List<TipoEvento>) tipoEventoDao.findAll();
    }

    @Override
    @Transactional
    public void save(TipoEvento tipoEvento) {
        tipoEventoDao.save(tipoEvento);

    }

    @Override
    public TipoEvento findOne(Long id) {
        return tipoEventoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        tipoEventoDao.deleteById(id);
    }
}
