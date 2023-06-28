package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.IRoleDao;
import com.tesis.payticket.models.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    private IRoleDao roleDao;

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return (List<Role>) roleDao.findAll();
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findOne(Long id) {
        return roleDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        roleDao.deleteById(id);
    }
}
