package com.tesis.payticket.models.dao;

import com.tesis.payticket.models.entity.Boleto;
import org.springframework.data.repository.CrudRepository;

public interface IBoletoDao extends CrudRepository<Boleto, Long> {
}
