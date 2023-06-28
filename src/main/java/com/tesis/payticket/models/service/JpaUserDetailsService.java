package com.tesis.payticket.models.service;

import com.tesis.payticket.models.dao.IUsuarioDao;
import com.tesis.payticket.models.entity.Role;
import com.tesis.payticket.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario= usuarioDao.findByEmail(email);
        if(usuario == null){
            logger.error("Error de incicio de sesión: no existe el usuario con el correo '"+email+"'");
            throw new UsernameNotFoundException("Error de inicio de sesión: no existe el usuario con el correo '"+email+"'");
        }

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

        for (Role role: usuario.getRoles()) {
            logger.info("Role: ".concat(role.getRol()));
            roles.add(new SimpleGrantedAuthority(role.getRol()));
        }

        if(roles.isEmpty()){
            logger.error("Error login: el usuario no tiene roles asignados!");
            throw new UsernameNotFoundException("Error login: el usuario no tiene roles asignados!");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, roles);

    }

}
