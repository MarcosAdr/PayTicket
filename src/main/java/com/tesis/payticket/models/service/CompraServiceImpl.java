package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.ICompraDao;
import com.tesis.payticket.models.entity.Compra;
import com.tesis.payticket.models.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraServiceImpl implements ICompraService{

    @Autowired
    private ICompraDao compraDao;


    @Override
    public List<Compra> findAll() {
        return (List<Compra>) compraDao.findAll();
    }

    @Override
    public void save(Compra compra) {
        compraDao.save(compra);
    }

    @Override
    public Compra findOne(Long id) {
        return compraDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        compraDao.deleteById(id);

    }
}
