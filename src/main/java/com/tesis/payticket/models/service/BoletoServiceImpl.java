package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.IBoletoDao;
import com.tesis.payticket.models.entity.Boleto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoletoServiceImpl implements IBoletoService{

    @Autowired
    private IBoletoDao boletoDao;



    @Override
    @Transactional
    public void save(Boleto boleto) {
        boletoDao.save(boleto);
    }

    @Override
    @Transactional(readOnly = true)
    public Boleto findOne(Long id) {
        return boletoDao.findById(id).orElse(null);
    }
}
