package org.moneyMinder.dao;

import org.moneyMinder.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    Usuario findByCorreoAndPass(String correo, String pass);

    Usuario findByCorreo(String correo);
}