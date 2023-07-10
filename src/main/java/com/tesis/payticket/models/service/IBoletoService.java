package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Boleto;

public interface IBoletoService {

    public void save(Boleto boleto);

    public Boleto findOne(Long id);
}
