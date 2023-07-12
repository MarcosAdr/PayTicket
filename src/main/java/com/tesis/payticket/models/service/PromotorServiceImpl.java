package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.ICompraDao;
import com.tesis.payticket.models.dao.IPromotorDao;
import com.tesis.payticket.models.entity.Compra;
import com.tesis.payticket.models.entity.Promotor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PromotorServiceImpl implements IPromotorService{

    @Autowired
    private IPromotorDao promotorDao;


    @Override
    @Transactional(readOnly = true)
    public List<Promotor> findAll() {
        return (List<Promotor>) promotorDao.findAll();
    }


    @Override
    @Transactional
    public void save(Promotor promotor){
        promotorDao.save(promotor);
    }

    @Override
    @Transactional(readOnly = true)
    public Promotor findOne(Long id) {
        return promotorDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        promotorDao.deleteById(id);}


}
