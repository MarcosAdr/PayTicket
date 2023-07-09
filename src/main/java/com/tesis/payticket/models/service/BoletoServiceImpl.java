package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.IBoletoDao;
import com.tesis.payticket.models.entity.Boleto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoletoServiceImpl implements IBoletoService{

    @Autowired
    private IBoletoDao boletoDao;


    @Override
    public void save(Boleto boleto) {
        boletoDao.save(boleto);
    }
}
