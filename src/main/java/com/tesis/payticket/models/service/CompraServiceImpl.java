package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.ICompraDao;
import com.tesis.payticket.models.entity.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompraServiceImpl implements ICompraService{

    @Autowired
    private ICompraDao compraDao;


    @Override
    @Transactional(readOnly = true)
    public List<Compra> findAll() {
        return (List<Compra>) compraDao.findAll();
    }

    @Override
    @Transactional
    public void save(Compra compra) {
        compraDao.save(compra);
    }

    @Override
    @Transactional(readOnly = true)
    public Compra findOne(Long id) {
        return compraDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        compraDao.deleteById(id);}

    @Override
    @Transactional(readOnly = true)
    public List<Compra> findByUsuarioId(Long id) {
        return compraDao.findByUsuarioId(id);
    }


}
