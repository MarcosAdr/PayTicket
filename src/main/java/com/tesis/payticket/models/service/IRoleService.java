package com.tesis.payticket.models.service;

import com.tesis.payticket.models.entity.Role;
import com.tesis.payticket.models.entity.Usuario;

import java.util.List;

public interface IRoleService {

    public List<Role> findAll();

    public void save(Role role);

    public Role findOne(Long id);

    public void delete(Long id);

}
