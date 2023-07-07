package com.tesis.payticket.models.dao;

import com.tesis.payticket.models.entity.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICompraDao extends CrudRepository<Compra, Long> {

    List<Compra> findByUsuarioId(Long id);

}
