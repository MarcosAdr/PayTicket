package com.tesis.payticket.models.dao;

import com.tesis.payticket.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

    public Usuario findByUsername(String username);// aqui puedo cambiar a buscarlo por el correo

}
