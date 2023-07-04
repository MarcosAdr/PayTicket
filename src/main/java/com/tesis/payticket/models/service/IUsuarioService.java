package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

    public List<Usuario> findAll();

    public void save(Usuario usuario);

    public Usuario findOne(Long id);

    public void delete(Long id);



 /*   public Role findByRol(String rol);*/
}
