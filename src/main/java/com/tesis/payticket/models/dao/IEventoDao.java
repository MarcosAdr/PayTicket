package com.tesis.payticket.models.dao;

import com.tesis.payticket.models.entity.Evento;
import org.springframework.data.repository.CrudRepository;


public interface IEventoDao extends CrudRepository<Evento, Long> {

    /*List<Evento> findByNombreContainingIgnoreCase(String nombre);  //revisar  */

}
