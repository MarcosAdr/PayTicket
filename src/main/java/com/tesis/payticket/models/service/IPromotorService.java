package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Promotor;

import java.util.List;

public interface IPromotorService {

    public List<Promotor> findAll();

    public void save(Promotor promotor);

    public Promotor findOne(Long id);

    public void delete(Long id);
}
